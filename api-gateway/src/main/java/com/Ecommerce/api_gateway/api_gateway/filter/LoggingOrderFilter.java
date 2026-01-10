package com.Ecommerce.api_gateway.api_gateway.filter;

import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cloud.gateway.filter.GatewayFilter;
import org.springframework.cloud.gateway.filter.factory.AbstractGatewayFilterFactory;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class LoggingOrderFilter extends AbstractGatewayFilterFactory<LoggingOrderFilter.Config> {

    //below are the constructor
    public LoggingOrderFilter() {
        super(Config.class);
    }

//    public LoggingOrderFilter(Class<Config> configClass) {
//        super(configClass);
//    }

    @Override
    public GatewayFilter apply(Config config) {
        return (exchange, chain) -> {
            log.info("filter in the order services " + exchange.getRequest().getURI());
            return chain.filter(exchange);
        };
    }

    @Data
    public static class  Config
    {
        //here we can declare some variable and then we can perform any operation with the help of that config setting
    }
}
