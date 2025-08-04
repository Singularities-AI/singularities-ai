package com.singularities.api.service;

import com.singularities.api.data.entity.ModelModel;
import com.singularities.api.data.repository.ModelRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.ai.ollama.api.OllamaApi;
import org.springframework.http.HttpMethod;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.scheduler.Schedulers;

@Service
@RequiredArgsConstructor
public class OllamaService {

    private final ModelRepository modelRepository;
    private final WebClient webClient;

    @Async
    public void pullModel(ModelModel model) {
        webClient.post()
                .uri("/api/pull")
                .bodyValue(new OllamaApi.PullModelRequest(model.getName()))
                .retrieve()
                .bodyToFlux(Void.class)
                .collectList()
                .publishOn(Schedulers.boundedElastic())
                .doOnSuccess(list -> {
                    //update model row after download
                    model.setDownload(true);
                    model.setDownloading(false);
                    modelRepository.save(model);
                }).block();
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
                    model.setDownload(false);
                    model.setDownloading(false);
                    modelRepository.save(model);
                }).block();
    }
}
