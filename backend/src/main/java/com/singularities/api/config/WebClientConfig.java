package com.singularities.api.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.client.WebClient;

@Configuration
public class WebClientConfig {

    @Value("${spring.ai.ollama.base-url}")
    String url;

    @Bean
    public WebClient ollamaWebClient() {
        return WebClient.builder()
                .baseUrl(url)
                .build();
    }
}