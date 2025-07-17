package com.singularities.api.service;

import com.singularities.api.data.constant.EMessageRole;
import com.singularities.api.data.entity.ChatModel;
import com.singularities.api.data.entity.MessageModel;
import com.singularities.api.data.entity.UserModel;
import com.singularities.api.data.repository.ChatRepository;
import com.singularities.api.dto.request.ChatUpdateRequestDto;
import com.singularities.api.dto.request.MessageRequestDto;
import com.singularities.api.exception.SingularitiesAIForbiddenException;
import com.singularities.api.exception.SingularitiesAINotFoundException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.ai.chat.messages.AssistantMessage;
import org.springframework.ai.chat.messages.Message;
import org.springframework.ai.chat.messages.UserMessage;
import org.springframework.ai.chat.model.ChatResponse;
import org.springframework.ai.chat.prompt.Prompt;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

import static com.singularities.api.exception.ExceptionMessage.*;

@Slf4j
@Service
@RequiredArgsConstructor
public class ChatService {

    private final ModelService modelService;
    private final MessageService messageService;
    private final ChatRepository chatRepository;

    //SPRING AI
    private final org.springframework.ai.chat.model.ChatModel chatModelAI;


    private ChatModel create(UserModel user, String firstMessage) {
        ChatModel chatModel = new ChatModel();
        chatModel.setCreationDate(LocalDateTime.now());
        chatModel.setUser(user);
        chatModel.setTitle(firstMessage.substring(5));
        return chatRepository.save(chatModel);
    }


    public ChatModel update(UserModel user, UUID uuid, ChatUpdateRequestDto form) {
        ChatModel chat = chatRepository.findById(uuid).orElseThrow(
                () -> new SingularitiesAINotFoundException(String.format(CHAT_NOT_FOUND, uuid))
        );
        if(!chat.getUser().equals(user)) {
            throw new SingularitiesAIForbiddenException(String.format(CHAT_UPDATE_FORBIDDEN, uuid));
        }

        chat.setContext(form.getContext());
        chat.setModel(modelService.findByUUID(form.getModelUUID()));
        return chatRepository.save(chat);
    }


    public Page<ChatModel> findAllByUser(UserModel user, Pageable pageable) {
        return chatRepository.findAllByUser(user, pageable);
    }


    public void deleteByUserAndUUID(UserModel user, UUID uuid) {
        ChatModel chat = chatRepository.findById(uuid).orElseThrow(
                () -> new SingularitiesAINotFoundException(String.format(CHAT_NOT_FOUND, uuid))
        );
        if(!chat.getUser().equals(user)) {
            throw new SingularitiesAIForbiddenException(String.format(CHAT_DELETE_FORBIDDEN, uuid));
        }
        chatRepository.deleteById(uuid);
    }


    public List<MessageModel> listMessages(UserModel user, UUID uuid) {
        ChatModel chatModel = chatRepository.findById(uuid).orElseThrow(
                () -> new SingularitiesAINotFoundException(String.format(CHAT_NOT_FOUND, uuid))
        );
        if(!chatModel.getUser().equals(user)) {
            throw new SingularitiesAIForbiddenException(String.format(CHAT_ACCESS_FORBIDDEN, uuid));
        }
        return chatModel.getMessages();
    }


    public MessageModel addMessageToChat(UserModel user, MessageRequestDto form) {
        ChatModel chatModel;
        if(form.getChatUUID() == null) {
            //create new chat
            chatModel = create(user, form.getMessage());
        } else {
            chatModel = chatRepository.findById(form.getChatUUID()).orElseThrow(
                    () -> new SingularitiesAINotFoundException(String.format(CHAT_NOT_FOUND, form.getChatUUID()))
            );
        }

        //add user message
        messageService.create(chatModel, EMessageRole.USER, form.getMessage());

        //gen agent response with chat histories
        List<Message> messages = chatModel.getMessages().stream()
                .map(msg -> {
                    return switch (msg.getRole()) {
                        case "USER" -> new UserMessage(msg.getContent());
                        case "AGENT" -> new AssistantMessage(msg.getContent());
                        default -> throw new IllegalArgumentException("Unknown message role: " + msg.getRole());
                    };
                })
                .collect(Collectors.toList());

        Prompt prompt = new Prompt(chatModel.getContext() + messages); //add context to all request
        ChatResponse aiResponse = chatModelAI.call(prompt);
        String aiMessageContent = aiResponse.getResult().getOutput().getText();
        return messageService.create(chatModel, EMessageRole.AGENT, aiMessageContent);
    }
}
