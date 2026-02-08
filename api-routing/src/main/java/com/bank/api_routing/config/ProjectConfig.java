package com.bank.api_routing.config;

import org.springframework.cloud.gateway.filter.ratelimit.KeyResolver;
import org.springframework.cloud.gateway.filter.ratelimit.RedisRateLimiter;
import org.springframework.cloud.gateway.route.RouteLocator;
import org.springframework.cloud.gateway.route.builder.RouteLocatorBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import reactor.core.publisher.Mono;
import java.time.Duration;

@Configuration
public class ProjectConfig {

        @Bean
        public RouteLocator routes(RouteLocatorBuilder builder) {
                return builder.routes()
                                .route("account-service",
                                                route -> route.path("/accounts/**")
                                                                .filters(f -> f.rewritePath(
                                                                                "/accounts/(?<remaining>.*)",
                                                                                "/${remaining}")
                                                                                .circuitBreaker(config -> config
                                                                                                .setName("accountServiceCircuitBreaker")
                                                                                                .setFallbackUri("forward:/fallback/accounts"))
                                                                                .requestRateLimiter(config -> config
                                                                                                .setRateLimiter(redisRateLimiter())
                                                                                                .setKeyResolver(keyResolver())))
                                                                .uri("lb://ACCOUNT-SERVICE"))
                                .route("transaction-service",
                                                route -> route.path("/transactions/**")
                                                                .filters(f -> f.rewritePath(
                                                                                "/transactions/(?<remaining>.*)",
                                                                                "/${remaining}")
                                                                                .retry(config -> config
                                                                                                .setRetries(3)
                                                                                                .setMethods(HttpMethod.GET)
                                                                                                .setStatuses(org.springframework.http.HttpStatus.SERVICE_UNAVAILABLE)
                                                                                                .setBackoff(Duration
                                                                                                                .ofMillis(1000),
                                                                                                                Duration.ofMillis(
                                                                                                                                2000),
                                                                                                                2,
                                                                                                                true))

                                                                                .requestRateLimiter(config -> config
                                                                                                .setRateLimiter(redisRateLimiter())
                                                                                                .setKeyResolver(keyResolver()))

                                                                )

                                                                .uri("lb://TRANSACTION-SERVICE")

                                )
                                .build();
        }

        @SuppressWarnings("null")
        @Bean
        public KeyResolver keyResolver() {
                return exchange -> Mono.just(
                                exchange.getRequest().getRemoteAddress() != null
                                                ? exchange.getRequest().getRemoteAddress().getAddress().getHostAddress()
                                                : "unknown");
        }

        @Bean
        public RedisRateLimiter redisRateLimiter() {
                return new RedisRateLimiter(17, 1000, 1);
        }
}
