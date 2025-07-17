package com.singularities.api.data.repository;

import com.singularities.api.data.entity.RoleModel;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
import java.util.UUID;

public interface RoleRepository extends JpaRepository<RoleModel, UUID> {

    Optional<RoleModel> findByName(String role);
}
