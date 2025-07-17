package com.singularities.api.service;

import com.singularities.api.data.entity.ModelModel;
import com.singularities.api.data.repository.ModelRepository;
import com.singularities.api.exception.SingularitiesAIConflictException;
import com.singularities.api.exception.SingularitiesAINotFoundException;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

import static com.singularities.api.exception.ExceptionMessage.MODEL_IS_ALREADY_DOWNLOAD;
import static com.singularities.api.exception.ExceptionMessage.MODEL_NOT_FOUND;

@Slf4j
@Service
@RequiredArgsConstructor
public class ModelService {

    private final ModelRepository modelRepository;


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

        //TODO Run Ollama download

        //update model row
        model.setDownload(true);
        modelRepository.save(model);
    }


    public void delete(UUID uuid) {
        ModelModel model = findByUUID(uuid);

        if(model.isDownload()) {
            //TODO Run Ollama command to delete model

            //update model row
            model.setDownload(false);
            modelRepository.save(model);
        }
    }

}
