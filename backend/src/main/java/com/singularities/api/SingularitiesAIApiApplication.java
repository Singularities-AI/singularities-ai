package com.singularities.api;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Info;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.annotation.EnableScheduling;

@EnableAsync
@EnableJpaAuditing
@EnableScheduling
@SpringBootApplication
@OpenAPIDefinition(info = @Info(title = "Singularities AI API", version = "v1"))
public class SingularitiesAIApiApplication {
    public static void main(String[] args) {
        SpringApplication.run(SingularitiesAIApiApplication.class, args);
    }
}