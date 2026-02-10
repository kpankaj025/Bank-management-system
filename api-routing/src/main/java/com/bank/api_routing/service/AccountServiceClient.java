package com.bank.api_routing.service;

import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

import com.bank.api_routing.dto.LoginRequest;
import com.bank.api_routing.dto.RegistrationDto;
import com.bank.api_routing.dto.UserResponse;

import reactor.core.publisher.Mono;

@Service
public class AccountServiceClient {

    private WebClient.Builder webClient;

    public AccountServiceClient(WebClient.Builder webClient) {
        this.webClient = webClient;
    }

    public Mono<UserResponse> login(LoginRequest request) {
        return webClient.baseUrl("lb://ACCOUNT-SERVICE").build().post()
                .uri("/api/v1/auth/login")
                .bodyValue(request)
                .retrieve()
                .bodyToMono(UserResponse.class);
    }

    public Mono<RegistrationDto> register(RegistrationDto dto) {
        return webClient.baseUrl("lb://ACCOUNT-SERVICE").build().post()
                .uri("/api/v1/accounts/register")
                .bodyValue(dto)
                .retrieve()
                .bodyToMono(RegistrationDto.class);
    }
}
