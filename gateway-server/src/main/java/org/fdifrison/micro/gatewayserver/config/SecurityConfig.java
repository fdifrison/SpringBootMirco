package org.fdifrison.micro.gatewayserver.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.reactive.EnableWebFluxSecurity;
import org.springframework.security.config.web.server.ServerHttpSecurity;
import org.springframework.security.web.server.SecurityWebFilterChain;

@Configuration
@EnableWebFluxSecurity
public class SecurityConfig {

    @Bean
    public SecurityWebFilterChain securityFilterChain(ServerHttpSecurity security) {
        security.authorizeExchange(
                        // N.B. order is essential in filter-chain,
                        // the permission that comes first can override the ones that following one
                        // exchange -> exchange.anyExchange().authenticated() // authenticate any exchange
                        exchange -> exchange.pathMatchers(HttpMethod.GET).permitAll() // read-only is permitted without authentication
                                .pathMatchers("/fdifrison/accounts/**").authenticated()
                                .pathMatchers("/fdifrison/cards/**").authenticated()
                                .pathMatchers("/fdifrison/loans/**").authenticated())
                .oauth2ResourceServer(
                        oAuth2ResourceServerSpec -> oAuth2ResourceServerSpec
                                .jwt(Customizer.withDefaults()));
        security.csrf(ServerHttpSecurity.CsrfSpec::disable); // not needed since there is no browser involved in our app
        return security.build();
    }

}
