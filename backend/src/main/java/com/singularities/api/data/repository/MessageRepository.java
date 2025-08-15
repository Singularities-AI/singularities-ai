package com.singularities.api.data.repository;

import com.singularities.api.data.entity.MessageModel;
import com.singularities.api.data.entity.UserModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.UUID;

public interface MessageRepository extends JpaRepository<MessageModel, UUID> {

    @Modifying
    @Query("DELETE FROM MessageModel m WHERE m.chat.user = :user")
    void deleteAllByUser(@Param("user") UserModel user);
}