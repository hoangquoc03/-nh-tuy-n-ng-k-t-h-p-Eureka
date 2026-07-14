package org.example.apigateway.config;


import org.springframework.cloud.gateway.route.RouteLocator;
import org.springframework.cloud.gateway.route.builder.RouteLocatorBuilder;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;



@Configuration
public class GatewayConfig {


    @Bean
    public RouteLocator customRoutes(
            RouteLocatorBuilder builder
    ){


        return builder.routes()


                // Customer Service
                .route(
                        "customer-service",
                        r -> r.path("/customers/**")
                                .uri("lb://customer-service")
                )


                // Product Service
                .route(
                        "product-service",
                        r -> r.path("/products/**")
                                .uri("lb://product-service")
                )


                // Order Service
                .route(
                        "order-service",
                        r -> r.path("/orders/**")
                                .uri("lb://order-service")
                )


                .build();

    }

}