package org.fdifrison.micro.gatewayserver.filter;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpHeaders;
import reactor.core.publisher.Mono;

@Configuration
public class ResponseTraceFilter {

    // a different way to create a filter instead of implementing the GlobalFilter interface,
    // we can define a @Bean GlobalFilter inside a configuration class

    private static final Logger logger = LoggerFactory.getLogger(ResponseTraceFilter.class);


    private final FilterUtility filterUtility;

    public ResponseTraceFilter(FilterUtility filterUtility) {
        this.filterUtility = filterUtility;
    }

    @Bean
    public GlobalFilter postGlobalFilter() {
        return (exchange, chain) -> chain.filter(exchange)
                .then(Mono.fromRunnable( // "then" means after microservice respond to a specific request
                        () -> {
                            HttpHeaders requestHeaders = exchange.getRequest().getHeaders();
                            // I'm fetching the correlation-id from the request
                            String correlationId = filterUtility.getCorrelationId(requestHeaders);
                            logger.debug("Updated the correlation id to the outbound headers: {}", correlationId);
                            // and setting the same correlation-id into the response so that these can be linked together
                            // later on if we need to debug or whatnot
                            exchange.getResponse().getHeaders().add(FilterUtility.CORRELATION_ID, correlationId);
                        }
                ));
    }
}
