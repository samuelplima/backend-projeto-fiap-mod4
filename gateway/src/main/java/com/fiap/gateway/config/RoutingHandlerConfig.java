package com.fiap.gateway.config;

import org.springframework.cloud.gateway.route.RouteLocator;
import org.springframework.cloud.gateway.route.builder.RouteLocatorBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RoutingHandlerConfig {

    public static final String PRODUCTS = "http://localhost:8081/";
    public static final String REGISTRATION = "http://localhost:8080/";


    @Bean
    public RouteLocator customRouteLocator(RouteLocatorBuilder routeLocatorBuilder) {
        return routeLocatorBuilder.routes()
                .route(r -> r
                        .path("/products/**")
                        .filters(f -> f.rewritePath("/products/(?<remains>.*)", "/${remains}"))
                        .uri(PRODUCTS)
                )
                .route(r -> r
                        .path("/registration/**")
                        .filters(f -> f.rewritePath("/registration/(?<remains>.*)", "/${remains}"))
                        .uri(REGISTRATION)
                )
                .build();
    }

}
