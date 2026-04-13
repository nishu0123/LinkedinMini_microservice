package com.Ecommerce.api_gateway.api_gateway.filter;

import com.Ecommerce.api_gateway.api_gateway.Service.JwtService;
import io.jsonwebtoken.JwtException;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cloud.gateway.filter.GatewayFilter;
import org.springframework.cloud.gateway.filter.factory.AbstractGatewayFilterFactory;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;


/*
At this time the security is not the production grade , later apply in more secure way
Spring Security → permitAll()
Custom Gateway Filter → validates JWT
Services → trust Gateway


//at this time we are at phase1.
🧱 Phase 1 – Basic Gateway JWT (Where You Are)

✔ Token validation
✔ Route protection
✔ Headers forwarded
✔ Central entry point

Good for:

Internal systems

Learning microservices

Controlled environments

Weakness:

Services trust Gateway blindly

No method-level security

No role-based enforcement at framework level

🛡️ Phase 2 – Proper Spring Security Integration (Next Step)

Move JWT validation into Spring Security:

Implement ReactiveAuthenticationManager

Create AuthenticationWebFilter

Store Authentication in SecurityContext

Use .hasRole("ADMIN")

Now you gain:

Framework-level RBAC

401 vs 403 separation

Method-level security (@PreAuthorize)

Cleaner architecture

Security level jumps significantly.

🔐 Phase 3 – Zero Trust Between Services

This is where real enterprise systems operate.

Instead of:

Service trusts headers from Gateway


You do:

Every service validates JWT independently


So even if someone bypasses Gateway:

❌ They still can’t access services.

This is how:

Netflix

Amazon

PayPal

design internal APIs.

🔒 Phase 4 – Advanced Hardening

Now we go serious enterprise:

Refresh token rotation

Token expiry short-lived (5–15 mins)

Blacklist / revoke tokens

Rate limiting

IP throttling

Audit logging

mTLS between services

Centralized authorization server (OAuth2)

Now you are in production-grade architecture.

 */

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
        /*
        | Field Name  | Getter Generated | Setter Generated | Property Name |
| ----------- | ---------------- | ---------------- | ------------- |
| `isActive`  | `isActive()`     | `setActive()`    | `active`      |
| `isEnabled` | `isEnabled()`    | `setEnabled()`   | `enabled`     |

so according to it getter or setter will it will generate  setAuthenticationEnabled and set the property AuthenticationEnabled
thats why AuthenticationEnabled = true in .yml file not isAuthenticationEnabled
         */


        private boolean isAuthenticationEnabled;
        private boolean isAuthorizationEnabled;
    }

    @Override
    public GatewayFilter apply(Config config) {


        return (exchange, chain) -> {

            log.info("Auth request uri " + exchange.getRequest().getURI());
            /*Lets implement the authentication */

            //fetching the header and logging for the debug purpose
            String authHeader = exchange.getRequest()
                    .getHeaders()
                    .getFirst(HttpHeaders.AUTHORIZATION);


            log.info("Authorization Header: " + authHeader);


            if (!config.isAuthenticationEnabled) {
                log.info("isAuthenticationEnabled = " +  config.isAuthenticationEnabled);
                return chain.filter(exchange);
            }

            String authorizationHeader =
                    exchange.getRequest().getHeaders().getFirst("Authorization");

            if (authorizationHeader == null || !authorizationHeader.startsWith("Bearer ")) {
                log.info("Missing token");
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
//                System.out.println("Extracted Username: " + username);
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