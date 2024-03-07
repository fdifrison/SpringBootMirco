package org.fdifrison.micro.gatewayserver;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.gateway.route.RouteLocator;
import org.springframework.cloud.gateway.route.builder.RouteLocatorBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.http.HttpMethod;

import java.time.Duration;
import java.time.LocalDateTime;

import static org.springframework.cloud.gateway.support.RouteMetadataUtils.CONNECT_TIMEOUT_ATTR;
import static org.springframework.cloud.gateway.support.RouteMetadataUtils.RESPONSE_TIMEOUT_ATTR;

@SpringBootApplication
public class GatewayServerApplication {

    public static void main(String[] args) {
        SpringApplication.run(GatewayServerApplication.class, args);
    }

    // Create a custom redirect policy to allow for "fdifrison" in front of ms names
    @Bean
    public RouteLocator fdifrisonRouteConfig(RouteLocatorBuilder builder) {
        return builder.routes()
                .route(path -> path.path("/fdifrison/accounts/**")
                        .filters(f -> f.rewritePath("/fdifrison/accounts/(?<segment>.*)", "/${segment}")
                                .addResponseHeader("X-Response-Time", LocalDateTime.now().toString())
                                .circuitBreaker(config -> config.setName("accountsCircuitBreaker")
                                        .setFallbackUri("forward:/contactSupport")))
                        .metadata(RESPONSE_TIMEOUT_ATTR, 800) // custom way
                        // to define or override yml configuration for the http timeout config
                        .metadata(CONNECT_TIMEOUT_ATTR, 800)
                        .uri("lb://ACCOUNTS")) // ms name all CAPS as defined inside the eureka server
                .route(path -> path.path("/fdifrison/loans/**")
                        .filters(f -> f.rewritePath("/fdifrison/loans/(?<segment>.*)", "/${segment}")
                                .addResponseHeader("X-Response-Time", LocalDateTime.now().toString())
                                // we need to assure that the methods that we give the retry capabilities are actually idempotent
                                // here we are setting it only on GET methods
                                .retry(retryConfig -> retryConfig.setRetries(3)
                                        .setMethods(HttpMethod.GET)
                                        .setBackoff(Duration.ofMillis(100), Duration.ofSeconds(1), 2, true)))
                                        // the backoff value is the delay we give at each retry operation
                        .uri("lb://LOANS"))
                .route(path -> path.path("/fdifrison/cards/**")
                        .filters(f -> f.rewritePath("/fdifrison/cards/(?<segment>.*)", "/${segment}")
                                .addResponseHeader("X-Response-Time", LocalDateTime.now().toString()))
                        .uri("lb://CARDS"))
                .build();
    }

}
