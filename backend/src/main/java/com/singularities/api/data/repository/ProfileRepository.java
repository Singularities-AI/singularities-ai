package com.singularities.api.data.repository;

import com.singularities.api.data.entity.ProfileModel;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface ProfileRepository extends JpaRepository<ProfileModel, UUID> {
}