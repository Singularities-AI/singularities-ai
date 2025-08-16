package com.singularities.api.data.repository;

import com.singularities.api.data.constant.ESettingKey;
import com.singularities.api.data.entity.SettingModel;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
import java.util.UUID;

public interface SettingRepository extends JpaRepository<SettingModel, UUID> {
    Optional<SettingModel> findByKey(ESettingKey key);
}