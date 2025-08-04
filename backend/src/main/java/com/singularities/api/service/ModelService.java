package com.singularities.api.service;

import com.singularities.api.data.entity.ModelModel;
import com.singularities.api.data.repository.ModelRepository;
import com.singularities.api.exception.SingularitiesAIBadRequestException;
import com.singularities.api.exception.SingularitiesAIConflictException;
import com.singularities.api.exception.SingularitiesAINotFoundException;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

import static com.singularities.api.exception.ExceptionMessage.*;

@Slf4j
@Service
@RequiredArgsConstructor
public class ModelService {

    private final ModelRepository modelRepository;
    private final OllamaService ollamaService;


    public ModelModel findByUUID(UUID uuid) {
        return modelRepository.findById(uuid).orElseThrow(
                () -> new SingularitiesAINotFoundException(String.format(MODEL_NOT_FOUND, uuid))
        );
    }

    public List<ModelModel> findAll() {
        return modelRepository.findAll();
    }


    public List<ModelModel> findAllAvailable() {
        return modelRepository.findAllByIsDownload(true);
    }


    @Transactional
    public void download(UUID uuid) {
        ModelModel model = findByUUID(uuid);

        if(model.isDownload()) {
            throw new SingularitiesAIConflictException(String.format(MODEL_IS_ALREADY_DOWNLOAD, uuid));
        }

        //run async ollama download
        ollamaService.pullModel(model);

        model.setDownloading(true);
        modelRepository.save(model);
    }


    @Transactional
    public void delete(UUID uuid) {
        ModelModel model = findByUUID(uuid);

        if(model.isDownloading()) {
            throw new SingularitiesAIBadRequestException(String.format(MODEL_IS_CURRENTLY_IN_DOWNLOAD, uuid));
        }

        if(model.isDefault()) {
            throw new SingularitiesAIBadRequestException(String.format(MODEL_IS_CURRENTLY_DEFAULT, uuid));
        }

        if(model.isDownload()) {
            //run async ollama delete
            ollamaService.deleteModel(model);
        }
    }


    @Transactional
    public void setDefault(UUID uuid) {
        ModelModel model = findByUUID(uuid);

        if(!model.isDownload()) {
            throw new SingularitiesAIBadRequestException(String.format(MODEL_IS_NOT_DOWNLOAD, uuid));
        }

        modelRepository.clearDefaultModels();

        model.setDefault(true);
        modelRepository.save(model);
    }
}
