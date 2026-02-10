package com.bank.api_routing.exception;

import java.util.Map;

import org.springframework.boot.autoconfigure.web.WebProperties;
import org.springframework.boot.autoconfigure.web.reactive.error.AbstractErrorWebExceptionHandler;
import org.springframework.boot.web.error.ErrorAttributeOptions;
import org.springframework.boot.web.reactive.error.ErrorAttributes;
import org.springframework.context.ApplicationContext;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.codec.ServerCodecConfigurer;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.server.RequestPredicates;
import org.springframework.web.reactive.function.server.RouterFunction;
import org.springframework.web.reactive.function.server.RouterFunctions;
import org.springframework.web.reactive.function.server.ServerResponse;

import com.bank.api_routing.dto.ErrorResponse;

@Component
@Order(-2)
public class GlobalErrorHandler extends AbstractErrorWebExceptionHandler {

    public GlobalErrorHandler(ErrorAttributes errorAttributes,
            ApplicationContext applicationContext,
            ServerCodecConfigurer codecConfigurer) {

        super(errorAttributes, new WebProperties.Resources(), applicationContext);
        super.setMessageWriters(codecConfigurer.getWriters());
        super.setMessageReaders(codecConfigurer.getReaders());
    }

    @Override
    protected RouterFunction<ServerResponse> getRoutingFunction(
            ErrorAttributes errorAttributes) {

        return RouterFunctions.route(RequestPredicates.all(), request -> {

            Map<String, Object> error = getErrorAttributes(
                    request,
                    ErrorAttributeOptions.of(
                            ErrorAttributeOptions.Include.MESSAGE));

            int statusCode = (int) error.getOrDefault("status", 500);

            HttpStatus status = HttpStatus.valueOf(statusCode);

            ErrorResponse response = new ErrorResponse(
                    (String) error.getOrDefault("message", "Gateway error"),
                    status);

            return ServerResponse.status(status)
                    .contentType(MediaType.APPLICATION_JSON)
                    .bodyValue(response);
        });
    }
}
