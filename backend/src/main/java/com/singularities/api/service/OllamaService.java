package com.singularities.api.service;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.singularities.api.data.entity.ModelModel;
import com.singularities.api.data.repository.ModelRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.ai.ollama.api.OllamaApi;
import org.springframework.http.HttpMethod;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.scheduler.Schedulers;

import java.time.Duration;

@Slf4j
@Service
@RequiredArgsConstructor
public class OllamaService {

    private final ModelRepository modelRepository;
    private final WebClient webClient;

    ObjectMapper mapper = new ObjectMapper();

    @Async
    public void pullModel(ModelModel model) {
        webClient.post()
                .uri("/api/pull")
                .bodyValue(new OllamaApi.PullModelRequest(model.getName()))
                .retrieve()
                .bodyToFlux(String.class)
                .publishOn(Schedulers.boundedElastic())
                .sample(Duration.ofSeconds(5)) //log every 5 seconds
                .doOnNext(chunk -> {
                    try {
                        JsonNode node = mapper.readTree(chunk);
                        if (node.has("total") && node.has("completed")) {
                            long total = node.get("total").asLong();
                            long completed = node.get("completed").asLong();
                            int percent = (int) ((completed * 100) / total);
                            log.info("Download model {} Progress: {}%", model.getCode(), percent);
                        }
                    } catch (Exception e) {
                        log.error("Download model {} Invalid chunk: {}", model.getCode(), chunk);
                    }
                })
                .doOnComplete(() -> {
                    // update model row after download
                    log.info("Download model {} successfully", model.getCode());
                    model.setDownload(true);
                    model.setDownloading(false);
                    modelRepository.save(model);
                })
                .subscribe();
    }


    @Async
    public void deleteModel(ModelModel model) {
        webClient.method(HttpMethod.DELETE)
                .uri("/api/delete")
                .bodyValue(new OllamaApi.DeleteModelRequest(model.getName()))
                .retrieve()
                .bodyToMono(Void.class)
                .publishOn(Schedulers.boundedElastic())
                .doOnSuccess(unused -> {
                    //update model row after delete
                    log.info("Delete model {} successfully", model.getCode());
                    model.setDownload(false);
                    model.setDownloading(false);
                    modelRepository.save(model);
                }).block();
    }
}
