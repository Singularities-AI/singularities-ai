package com.singularities.api.data.repository;

import com.singularities.api.data.entity.MessageModel;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface MessageRepository extends JpaRepository<MessageModel, UUID> {
}