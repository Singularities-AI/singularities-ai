package com.singularities.api.data.repository;

import com.singularities.api.data.entity.SettingValueModel;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface SettingValueRepository extends JpaRepository<SettingValueModel, UUID> {}