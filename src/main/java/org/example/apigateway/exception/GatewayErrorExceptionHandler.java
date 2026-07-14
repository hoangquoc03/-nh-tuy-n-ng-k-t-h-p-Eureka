package org.example.apigateway.exception;

import com.example.gateway.response.ApiResponseError;


import com.fasterxml.jackson.databind.ObjectMapper;


import org.springframework.boot.web.reactive.error.ErrorWebExceptionHandler;

import org.springframework.core.annotation.Order;

import org.springframework.http.HttpStatus;

import org.springframework.http.MediaType;

import org.springframework.stereotype.Component;

import org.springframework.web.server.ServerWebExchange;

import reactor.core.publisher.Mono;



import java.time.LocalDateTime;



@Component

@Order(-1)

public class GatewayErrorExceptionHandler
        implements ErrorWebExceptionHandler {



    private final ObjectMapper objectMapper;



    public GatewayErrorExceptionHandler(
            ObjectMapper objectMapper
    ){

        this.objectMapper = objectMapper;

    }



    @Override
    public Mono<Void> handle(
            ServerWebExchange exchange,
            Throwable ex
    ){


        exchange.getResponse()
                .getHeaders()
                .setContentType(
                        MediaType.APPLICATION_JSON
                );


        ApiResponseError error;



        /*
        Service bị chết
        */


        error = new ApiResponseError(

                LocalDateTime.now(),

                HttpStatus.SERVICE_UNAVAILABLE.value(),

                "Service Unavailable",

                "Cổng Gateway không thể kết nối tới dịch vụ đích"

        );



        exchange.getResponse()
                .setStatusCode(
                        HttpStatus.SERVICE_UNAVAILABLE
                );



        try {


            byte[] bytes =
                    objectMapper.writeValueAsBytes(error);



            return exchange.getResponse()
                    .writeWith(

                            Mono.just(
                                    exchange.getResponse()
                                            .bufferFactory()
                                            .wrap(bytes)
                            )

                    );


        } catch(Exception e){


            return Mono.error(e);

        }

    }

}