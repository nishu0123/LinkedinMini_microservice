package com.Ecommerce.api_gateway.api_gateway.filter;

import lombok.extern.java.Log;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

@Component
@Slf4j
public class GlobalLoggingFilter implements GlobalFilter {
    private static final Logger log = LoggerFactory.getLogger(GlobalLoggingFilter.class);

    public Mono<Void> filter(ServerWebExchange serverWebExchange , GatewayFilterChain chain)
    {
        //logging all the api
        log.info("logging the url of the api {}", serverWebExchange.getRequest().getURI());


        return chain.filter(serverWebExchange);
    }

}
