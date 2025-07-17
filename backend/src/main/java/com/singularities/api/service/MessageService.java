package com.singularities.api.service;

import com.singularities.api.data.constant.EMessageRole;
import com.singularities.api.data.entity.ChatModel;
import com.singularities.api.data.entity.MessageModel;
import com.singularities.api.data.repository.MessageRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Slf4j
@Service
@RequiredArgsConstructor
public class MessageService {

    private final MessageRepository messageRepository;


    public MessageModel create(ChatModel chat, EMessageRole role, String content) {
        MessageModel messageModel = new MessageModel();
        messageModel.setContent(content);
        messageModel.setRole(role.name());
        messageModel.setChat(chat);
        messageModel.setModel(chat.getModel()); //the model that was used at the time the message was requested.
        messageModel.setContext(chat.getContext()); //the context that was used at the time the message was requested.
        messageModel.setCreatedAt(LocalDateTime.now());
        return messageRepository.save(messageModel);
    }
}
