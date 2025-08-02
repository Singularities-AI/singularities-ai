package com.singularities.api.service;

import com.singularities.api.data.entity.ModelModel;
import com.singularities.api.data.repository.ModelRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.ai.ollama.management.OllamaModelManager;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class OllamaService {

    private final OllamaModelManager ollamaModelManager;
    private final ModelRepository modelRepository;

    @Async
    public void pullModel(ModelModel model) {
        ollamaModelManager.pullModel(model.getName());

        //update model row after download
        model.setDownload(true);
        modelRepository.save(model);
    }

    @Async
    public void deleteModel(ModelModel model) {
        ollamaModelManager.deleteModel(model.getName());

        //update model row after delete
        model.setDownload(false);
        modelRepository.save(model);
    }
}
