package com.singularities.api.service;

import com.singularities.api.data.entity.ChatModel;
import com.singularities.api.data.repository.ChatRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.ai.chat.model.ChatResponse;
import org.springframework.ai.chat.prompt.Prompt;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class GenerationService {

    private final PromptService promptService;
    private final ChatRepository chatRepository;

    //SPRING AI
    private final org.springframework.ai.chat.model.ChatModel chatModelAI;

    @Async
    public void genAndSetChatTitleFromFirstMessage(ChatModel chatModel, String firstMessage) {
        Prompt prompt = promptService.createChatTitleFromFirstMessagePrompt(chatModel.getModel(), firstMessage);
        ChatResponse aiResponse = chatModelAI.call(prompt);

        chatModel.setTitle(aiResponse.getResult().getOutput().getText());
        chatRepository.save(chatModel);
    }
}
