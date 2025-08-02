package com.singularities.api.config;

import org.springframework.ai.ollama.api.OllamaApi;
import org.springframework.ai.ollama.management.OllamaModelManager;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class OllamaConfig {

    @Value("${spring.ai.ollama.base-url}")
    private String url;

    @Bean
    public OllamaApi ollamaApi() {
        return new OllamaApi("http://" + url);
    }

    @Bean
    public OllamaModelManager ollamaModelManager(OllamaApi ollamaApi) {
        return new OllamaModelManager(ollamaApi);
    }
}
