package com.singularities.api.data.repository;

import com.singularities.api.data.entity.ModelModel;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.UUID;

public interface ModelRepository extends JpaRepository<ModelModel, UUID> {

    List<ModelModel> findAllByIsDownload(boolean isDownload);
}
