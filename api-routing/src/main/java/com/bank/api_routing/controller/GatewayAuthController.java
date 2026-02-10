package com.bank.api_routing.controller;

import java.util.Map;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.bank.api_routing.dto.LoginRequest;
import com.bank.api_routing.dto.RegistrationDto;
import com.bank.api_routing.dto.UserResponse;
import com.bank.api_routing.service.AccountServiceClient;
import com.bank.api_routing.service.JwtService;

import reactor.core.publisher.Mono;

@RestController
@RequestMapping("/auth")
public class GatewayAuthController {

    private final AccountServiceClient accountClient;
    private final JwtService jwtService;

    public GatewayAuthController(AccountServiceClient accountClient,
            JwtService jwtService) {
        this.accountClient = accountClient;
        this.jwtService = jwtService;
    }

    @PostMapping("/login")
    public Mono<ResponseEntity<Map<String, String>>> login(
            @RequestBody LoginRequest request) {

        return accountClient.login(request)
                .map(user -> {
                    String token = jwtService.generateToken(user);
                    return ResponseEntity.ok(Map.of("token", token));
                });
    }

    @PostMapping("/register")
    public Mono<ResponseEntity<RegistrationDto>> register(
            @RequestBody RegistrationDto registrationDto) {

        return accountClient.register(registrationDto)
                .map(savedUser -> ResponseEntity
                        .status(org.springframework.http.HttpStatus.CREATED)
                        .body(savedUser));
    }
}
