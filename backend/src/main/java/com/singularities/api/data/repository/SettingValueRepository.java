package com.singularities.api.data.repository;

import com.singularities.api.data.entity.SettingModel;
import com.singularities.api.data.entity.SettingValueModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.UUID;

public interface SettingValueRepository extends JpaRepository<SettingValueModel, UUID> {

    @Modifying
    @Query("DELETE FROM SettingValueModel v WHERE v.setting = :setting AND v.value = :value")
    void deleteBySettingAndValue(@Param("setting") SettingModel setting, @Param("value") String value);
}