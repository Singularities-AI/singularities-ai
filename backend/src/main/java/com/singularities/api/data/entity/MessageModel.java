package com.singularities.api.data.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "messages")
public class MessageModel extends AbstractModel {

    @ManyToOne
    @JoinColumn(name = "chat_id", nullable = false)
    private ChatModel chat;

    @Column(nullable = false)
    private String role;

    @Column(columnDefinition = "TEXT")
    private String context;

    @ManyToOne
    @JoinColumn(name = "model_id", nullable = false)
    private ModelModel model;

    @Column(nullable = false, columnDefinition = "TEXT")
    private String content;
}

