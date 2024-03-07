package org.fdifrison.micro.gatewayserver;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.gateway.route.RouteLocator;
import org.springframework.cloud.gateway.route.builder.RouteLocatorBuilder;
import org.springframework.context.annotation.Bean;

import java.time.LocalDateTime;

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
                                .circuitBreaker(config -> config.setName("accountsCircuitBreaker")))
                        .uri("lb://ACCOUNTS")) // ms name all CAPS as defined inside the eureka server
                .route(path -> path.path("/fdifrison/loans/**")
                        .filters(f -> f.rewritePath("/fdifrison/loans/(?<segment>.*)", "/${segment}")
                                .addResponseHeader("X-Response-Time", LocalDateTime.now().toString()))
                        .uri("lb://LOANS"))
                .route(path -> path.path("/fdifrison/cards/**")
                        .filters(f -> f.rewritePath("/fdifrison/cards/(?<segment>.*)", "/${segment}")
                                .addResponseHeader("X-Response-Time", LocalDateTime.now().toString()))
                        .uri("lb://CARDS"))
                .build();
    }

}
