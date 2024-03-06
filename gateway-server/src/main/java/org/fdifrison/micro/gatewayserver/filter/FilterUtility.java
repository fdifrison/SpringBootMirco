package org.fdifrison.micro.gatewayserver.filter;


import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;

import java.util.List;

@Component
public class FilterUtility {

    public static final String CORRELATION_ID = "fdifrison-correlation-id";

    public String getCorrelationId(HttpHeaders requestHeaders) {
        List<String> correlationIds = requestHeaders.get(CORRELATION_ID);
        if (correlationIds != null) {
            return correlationIds.stream().findFirst().orElse(null);
            }
        return null;
    }

    public ServerWebExchange setRequestHeader(ServerWebExchange exchange, String name, String value) {
        return exchange.mutate().request(exchange.getRequest().mutate().header(name, value).build()).build();
    }

    public ServerWebExchange setCorrelationId(ServerWebExchange exchange, String correlationId) {
        return this.setRequestHeader(exchange, CORRELATION_ID, correlationId);
    }

}