package com.bank.api_routing;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

@EnableDiscoveryClient
@SpringBootApplication
public class ApiRoutingApplication {

	public static void main(String[] args) {
		SpringApplication.run(ApiRoutingApplication.class, args);
	}

}
