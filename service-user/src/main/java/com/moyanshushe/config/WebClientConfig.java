package com.moyanshushe.config;

/*
 * Author: Napbad
 * Version: 1.0
 */

import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.cloud.loadbalancer.annotation.LoadBalancerClient;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.client.WebClient;

@Configuration
@LoadBalancerClient(name = "service-member")
public class WebClientConfig {
    @LoadBalanced
    @Bean
    WebClient.Builder webClientBuilder() {
        return WebClient.builder();
    }
}
