package com.singularities.api.controller;

import com.singularities.api.data.entity.UserModel;
import com.singularities.api.dto.request.ChatUpdateRequestDto;
import com.singularities.api.dto.request.MessageRequestDto;
import com.singularities.api.dto.response.ChatResponseDto;
import com.singularities.api.dto.response.MessageResponseDto;
import com.singularities.api.mapper.ChatMapper;
import com.singularities.api.mapper.MessageMapper;
import com.singularities.api.service.AuthService;
import com.singularities.api.service.ChatService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequiredArgsConstructor
@RequestMapping("/web/chats")
@CrossOrigin(origins = "*", maxAge = 3600)
public class ChatController {

    private final ChatService chatService;
    private final AuthService authService;

    private final ChatMapper chatMapper;
    private final MessageMapper messageMapper;

    //Chats ----

    @GetMapping("/")
    @ResponseStatus(HttpStatus.OK)
    @PreAuthorize("hasRole('USER')")
    public Page<ChatResponseDto> list(Pageable pageable) {
        UserModel user = authService.getUserByAuthentication(SecurityContextHolder.getContext().getAuthentication());
        return chatMapper.toDto(chatService.findAllByUser(user, pageable));
    }

    @DeleteMapping("/{uuid}")
    @ResponseStatus(HttpStatus.OK)
    @PreAuthorize("hasRole('USER')")
    public void delete(@PathVariable String uuid) {
        UserModel user = authService.getUserByAuthentication(SecurityContextHolder.getContext().getAuthentication());
        chatService.deleteByUserAndUUID(user, UUID.fromString(uuid));
    }

    @PutMapping("/{uuid}")
    @ResponseStatus(HttpStatus.ACCEPTED)
    @PreAuthorize("hasRole('USER')")
    public ChatResponseDto update(@PathVariable UUID uuid, @Valid @RequestBody ChatUpdateRequestDto form) {
        UserModel user = authService.getUserByAuthentication(SecurityContextHolder.getContext().getAuthentication());
        return chatMapper.toDto(chatService.update(user, uuid, form));
    }


    //Messages ----

    @PostMapping("/messages")
    @ResponseStatus(HttpStatus.OK)
    @PreAuthorize("hasRole('USER')")
    public MessageResponseDto addMessage(@Valid @RequestBody MessageRequestDto form) {
        UserModel user = authService.getUserByAuthentication(SecurityContextHolder.getContext().getAuthentication());
        return messageMapper.toDto(chatService.addMessageToChat(user, form));
    }


    @GetMapping("{uuid}/messages")
    @ResponseStatus(HttpStatus.OK)
    @PreAuthorize("hasRole('USER')")
    public List<MessageResponseDto> listMessages(@PathVariable String uuid) {
        UserModel user = authService.getUserByAuthentication(SecurityContextHolder.getContext().getAuthentication());
        return chatService.listMessages(user, UUID.fromString(uuid)).stream().map(messageMapper::toDto).toList();
    }
}