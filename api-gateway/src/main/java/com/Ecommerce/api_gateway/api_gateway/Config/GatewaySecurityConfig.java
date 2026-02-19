package com.Ecommerce.api_gateway.api_gateway.Config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.reactive.EnableWebFluxSecurity;
import org.springframework.security.config.web.server.ServerHttpSecurity;
import org.springframework.security.web.server.SecurityWebFilterChain;

@Configuration
@EnableWebFluxSecurity
public class GatewaySecurityConfig {

    @Bean
    public SecurityWebFilterChain securityWebFilterChain(ServerHttpSecurity http) {

        return http
                .csrf(ServerHttpSecurity.CsrfSpec::disable)  // disable CSRF for APIs
                .authorizeExchange(exchange -> exchange
                        // Permit login & signup endpoints
                        .pathMatchers("/api/v1/user/auth/login", "/api/v1/user/auth/signup").permitAll()

                        // Allow actuator if needed
                        .pathMatchers("/actuator/**").permitAll()

                        // Everything else requires authentication
                        .anyExchange().authenticated()
                )
                .httpBasic(ServerHttpSecurity.HttpBasicSpec::disable)  // disable default basic auth popup
                .formLogin(ServerHttpSecurity.FormLoginSpec::disable)  // disable default login page
                .build();
    }
}
