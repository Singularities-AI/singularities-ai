package com.singularities.api.data.repository;

import com.singularities.api.data.entity.ChatModel;
import com.singularities.api.data.entity.UserModel;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.UUID;

public interface ChatRepository extends JpaRepository<ChatModel, UUID> {

    Page<ChatModel> findAllByUser(UserModel user, Pageable pageable);

    void deleteAllByUser(UserModel user);

    @Modifying
    @Query("UPDATE ChatModel c SET c.agent = null WHERE c.agent.id = :agentId")
    void unlinkAllChatByAgentId(@Param("agentId") UUID agentId);
}
