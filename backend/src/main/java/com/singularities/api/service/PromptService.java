package com.singularities.api.service;

import com.singularities.api.data.entity.ChatModel;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.ai.chat.messages.AssistantMessage;
import org.springframework.ai.chat.messages.Message;
import org.springframework.ai.chat.messages.SystemMessage;
import org.springframework.ai.chat.messages.UserMessage;
import org.springframework.ai.chat.prompt.Prompt;
import org.springframework.ai.ollama.api.OllamaOptions;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Service
@RequiredArgsConstructor
public class PromptService {

    public Prompt createPromptWithContextsAndHistories(ChatModel chat, String globalContext) {
        List<Message> messages = new ArrayList<>();
        messages.add(new SystemMessage("Always respond in a structured manner using markdown."));

        if (globalContext != null && !globalContext.trim().isEmpty()) {
            messages.add(new SystemMessage("User context : " + globalContext));
        }

        if (chat.getContext() != null && !chat.getContext().trim().isEmpty()) {
            messages.add(new SystemMessage("Chat context : " + chat.getContext()));
        }

        //gen agent response with chat histories
        List<Message> chatMessages = chat.getMessages().stream()
                .map(msg -> {
                    return switch (msg.getRole()) {
                        case "USER" -> new UserMessage(msg.getContent());
                        case "AGENT" -> new AssistantMessage(msg.getContent());
                        default -> throw new IllegalArgumentException("Unknown message role: " + msg.getRole());
                    };
                })
                .collect(Collectors.toList());

        messages.addAll(chatMessages);

        //use chat ollama model
        OllamaOptions options = OllamaOptions.builder().model(chat.getModel().getCode()).build();
        return new Prompt(messages, options);
    }
}
