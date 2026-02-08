package com.bank.api_routing.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import reactor.core.publisher.Mono;

@RestController
@RequestMapping("/fallback")
public class FallbackController {

    @GetMapping("/accounts")
    public Mono<ResponseEntity<String>> accountsFallback() {
        return Mono.just(
                ResponseEntity
                        .status(org.springframework.http.HttpStatus.SERVICE_UNAVAILABLE)
                        .body("Accounts service is currently unavailable. Please try later."));
    }
}
