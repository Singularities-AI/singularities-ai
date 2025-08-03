package com.singularities.api.data.repository;

import com.singularities.api.data.entity.ModelModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.UUID;

public interface ModelRepository extends JpaRepository<ModelModel, UUID> {

    List<ModelModel> findAllByIsDownload(boolean isDownload);

    @Modifying
    @Query("UPDATE ModelModel m SET m.isDefault = false WHERE m.isDefault = true")
    void clearDefaultModels();
}
