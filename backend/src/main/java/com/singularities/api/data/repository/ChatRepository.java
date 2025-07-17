package com.singularities.api.data.repository;

import com.singularities.api.data.entity.ChatModel;
import com.singularities.api.data.entity.UserModel;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface ChatRepository extends JpaRepository<ChatModel, UUID> {

    Page<ChatModel> findAllByUser(UserModel user, Pageable pageable);
}
