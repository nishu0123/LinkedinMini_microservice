package com.Ecommerce.api_gateway.api_gateway.FilterTest;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.reactive.WebFluxTest;
import org.springframework.context.ApplicationContext;
import org.springframework.cloud.gateway.filter.factory.GatewayFilterFactory;
import org.springframework.context.annotation.ComponentScan;

import java.util.Arrays;

@WebFluxTest
@ComponentScan(basePackages = "com.Ecommerce.api_gateway.api_gateway.filter")
public class GatewayFilterLoadTest {

    @Autowired
    private ApplicationContext context;

    @Test
    void printLoadedFilters() {
        System.out.println("=== Loaded GatewayFilterFactory beans ===");
        context.getBeansOfType(GatewayFilterFactory.class)
                .keySet()
                .forEach(System.out::println);
    }
}
