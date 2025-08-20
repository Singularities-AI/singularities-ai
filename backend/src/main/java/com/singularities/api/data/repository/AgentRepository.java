package com.singularities.api.data.repository;

import com.singularities.api.data.entity.AgentModel;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface AgentRepository extends JpaRepository<AgentModel, UUID> {
}