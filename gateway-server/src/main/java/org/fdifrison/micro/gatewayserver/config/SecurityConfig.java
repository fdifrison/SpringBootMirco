package org.fdifrison.micro.gatewayserver.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.convert.converter.Converter;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AbstractAuthenticationToken;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.reactive.EnableWebFluxSecurity;
import org.springframework.security.config.web.server.ServerHttpSecurity;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationConverter;
import org.springframework.security.oauth2.server.resource.authentication.ReactiveJwtAuthenticationConverterAdapter;
import org.springframework.security.web.server.SecurityWebFilterChain;
import reactor.core.publisher.Mono;

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
//                                .pathMatchers("/fdifrison/accounts/**").hasRole("ACCOUNTS")
//                                .pathMatchers("/fdifrison/cards/**").hasRole("CARDS")
//                                .pathMatchers("/fdifrison/loans/**").hasRole("LOANS"))
                                .pathMatchers("/fdifrison/accounts/**").permitAll()
                                .pathMatchers("/fdifrison/cards/**").permitAll()
                                .pathMatchers("/fdifrison/loans/**").permitAll())
                .oauth2ResourceServer(
                        oAuth2ResourceServerSpec -> oAuth2ResourceServerSpec
                                .jwt(jwtSpec -> jwtSpec.jwtAuthenticationConverter(grantedAuthoritiesExtractor())));
        security.csrf(ServerHttpSecurity.CsrfSpec::disable); // not needed since there is no browser involved in our app
        return security.build();
    }

    private Converter<Jwt, Mono<AbstractAuthenticationToken>> grantedAuthoritiesExtractor() {
        JwtAuthenticationConverter jwtAuthenticationConverter = new JwtAuthenticationConverter();
        jwtAuthenticationConverter.setJwtGrantedAuthoritiesConverter(new KeycloakRoleConverter());
        return new ReactiveJwtAuthenticationConverterAdapter(jwtAuthenticationConverter);
    }


}
