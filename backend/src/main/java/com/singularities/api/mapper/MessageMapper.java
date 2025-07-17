package com.singularities.api.mapper;

import com.singularities.api.data.entity.MessageModel;
import com.singularities.api.dto.response.MessageResponseDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class MessageMapper {

    public MessageResponseDto toDto(MessageModel model) {
        MessageResponseDto dto = new MessageResponseDto();
        dto.setId(model.getId());
        dto.setContent(model.getContent());
        dto.setRole(model.getRole());
        dto.setCreatedAt(model.getCreatedAt());
        return dto;
    }
}
