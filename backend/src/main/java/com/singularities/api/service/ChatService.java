package com.singularities.api.service;

import com.singularities.api.data.constant.EMessageRole;
import com.singularities.api.data.entity.ChatModel;
import com.singularities.api.data.entity.MessageModel;
import com.singularities.api.data.entity.ModelModel;
import com.singularities.api.data.entity.UserModel;
import com.singularities.api.data.repository.ChatRepository;
import com.singularities.api.dto.request.ChatUpdateRequestDto;
import com.singularities.api.dto.request.MessageRequestDto;
import com.singularities.api.exception.SingularitiesAIForbiddenException;
import com.singularities.api.exception.SingularitiesAINotFoundException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.ai.chat.model.ChatResponse;
import org.springframework.ai.chat.prompt.Prompt;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;
import java.util.UUID;

import static com.singularities.api.exception.ExceptionMessage.*;

@Slf4j
@Service
@RequiredArgsConstructor
public class ChatService {

    private final ModelService modelService;
    private final PromptService promptService;
    private final MessageService messageService;
    private final ChatRepository chatRepository;

    //SPRING AI
    private final org.springframework.ai.chat.model.ChatModel chatModelAI;


    private ChatModel create(UserModel user, String firstMessage, ModelModel model, String context) {
        ChatModel chatModel = new ChatModel();
        chatModel.setUser(user);
        chatModel.setModel(model);
        chatModel.setContext(context);
        chatModel.setTitle(firstMessage.length() > 30 ? firstMessage.substring(0, 30) : firstMessage);
        return chatRepository.save(chatModel);
    }


    public ChatModel update(UserModel user, UUID uuid, ChatUpdateRequestDto form) {
        ChatModel chat = chatRepository.findById(uuid).orElseThrow(
                () -> new SingularitiesAINotFoundException(String.format(CHAT_NOT_FOUND, uuid))
        );

        if (!chat.getUser().equals(user)) {
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
        if (!chat.getUser().equals(user)) {
            throw new SingularitiesAIForbiddenException(String.format(CHAT_DELETE_FORBIDDEN, uuid));
        }
        chatRepository.deleteById(uuid);
    }


    public List<MessageModel> listMessages(UserModel user, UUID uuid) {
        ChatModel chatModel = chatRepository.findById(uuid).orElseThrow(
                () -> new SingularitiesAINotFoundException(String.format(CHAT_NOT_FOUND, uuid))
        );
        if (!chatModel.getUser().equals(user)) {
            throw new SingularitiesAIForbiddenException(String.format(CHAT_ACCESS_FORBIDDEN, uuid));
        }
        return chatModel.getMessages();
    }


    public MessageModel addMessageToChat(UserModel user, MessageRequestDto form) {
        ModelModel model = modelService.findByUUID(form.getModelUUID());

        ChatModel chatModel;
        if (form.getChatUUID() == null) {
            //create new chat
            chatModel = create(user, form.getContent(), model, form.getContext());
        } else {
            chatModel = chatRepository.findById(form.getChatUUID()).orElseThrow(
                    () -> new SingularitiesAINotFoundException(String.format(CHAT_NOT_FOUND, form.getChatUUID()))
            );

            chatModel = updateChatConfigurationIfChanged(chatModel, model, form.getContext());
        }

        //add user message
        MessageModel userMessage = messageService.create(chatModel, EMessageRole.USER, form.getContent());
        chatModel.getMessages().add(userMessage);

        Prompt prompt = promptService.createPromptWithContextsAndHistories(chatModel, user.getContext());
        ChatResponse aiResponse = chatModelAI.call(prompt);
        return messageService.create(chatModel, EMessageRole.AGENT, aiResponse.getResult().getOutput().getText());
    }


    private ChatModel updateChatConfigurationIfChanged(ChatModel chatModel, ModelModel newModel, String newContext) {
        boolean hasChanged = false;

        if (!Objects.equals(chatModel.getModel(), newModel)) {
            chatModel.setModel(newModel);
            hasChanged = true;
        }

        if (!Objects.equals(chatModel.getContext(), newContext)) {
            chatModel.setContext(newContext);
            hasChanged = true;
        }

        if (hasChanged) {
            chatModel = chatRepository.save(chatModel);
        }

        return chatModel;
    }

    public void deleteAllByUser(UserModel user) {
        chatRepository.deleteAllByUser(user);
    }
}
