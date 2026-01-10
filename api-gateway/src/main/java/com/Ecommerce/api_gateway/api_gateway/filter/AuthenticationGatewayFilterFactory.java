package com.Ecommerce.api_gateway.api_gateway.filter;

import com.Ecommerce.api_gateway.api_gateway.Service.JwtService;
import io.jsonwebtoken.JwtException;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cloud.gateway.filter.GatewayFilter;
import org.springframework.cloud.gateway.filter.factory.AbstractGatewayFilterFactory;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;


@Component
@Slf4j
public class AuthenticationGatewayFilterFactory
        extends AbstractGatewayFilterFactory<AuthenticationGatewayFilterFactory.Config> {

    private final JwtService jwtService;

    public AuthenticationGatewayFilterFactory(JwtService jwtService) {
        super(Config.class);
        this.jwtService = jwtService;
    }

    @Data
    public static class Config {
        private boolean isAuthenticationEnabled;
        private boolean isAuthorizationEnabled;
    }

    @Override
    public GatewayFilter apply(Config config) {


        return (exchange, chain) -> {

            log.info("Auth request uri " + exchange.getRequest().getURI());
            /*Lets implement the authentication */

            if (!config.isAuthenticationEnabled) {
                return chain.filter(exchange);
            }

            String authorizationHeader =
                    exchange.getRequest().getHeaders().getFirst("Authorization");

            if (authorizationHeader == null || !authorizationHeader.startsWith("Bearer ")) {
                exchange.getResponse().setStatusCode(HttpStatus.UNAUTHORIZED);
                return exchange.getResponse().setComplete();
            }

            String token = authorizationHeader.substring(7);


            //use try catch block to handle the exception
            String userRole = null;
            Long userId = null;
            try {
                userId = jwtService.extractUserId(token);
                userRole = jwtService.extractUserRole(token);
                ServerWebExchange mutatedExchange = exchange.mutate()
                        .request(exchange.getRequest()
                                .mutate()
                                .header("X-User-Id", userId.toString())
                                .header("X-User-Role", userRole)
                                .build())
                        .build();

                return chain.filter(mutatedExchange);
            } catch (JwtException ex) {
                log.error("exception in fetching user id and userRole " + ex.getLocalizedMessage());
                exchange.getResponse().setStatusCode(HttpStatus.UNAUTHORIZED); // set the exchange to unauthorised
                return exchange.getResponse().setComplete();
            }





            /*Lets implement the authorization */

            /*
            if(!config.isAuthorizationEnabled)return chain.filter(exchange); // passing the api to the downstream without checking for authorization
            //lets first check for the api path
            String apiPath = exchange.getRequest().getPath().toString();

            //check for the user role
            if (apiPath.startsWith("/admin") && !userRole.equals("ADMIN")) {
                exchange.getResponse().setStatusCode(HttpStatus.FORBIDDEN);
                return exchange.getResponse().setComplete();
            }

             */
        };
    }
}