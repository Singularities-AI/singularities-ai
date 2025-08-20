package com.singularities.api.mapper;

import com.singularities.api.data.entity.ChatModel;
import com.singularities.api.dto.response.ChatResponseDto;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.stereotype.Service;

import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ChatMapper {

    private final AbstractMapper<ChatModel, ChatResponseDto> abstractMapper;

    public ChatResponseDto toDto(ChatModel model) {
        ChatResponseDto dto = new ChatResponseDto();
        dto.setId(model.getId());
        dto.setTitle(model.getTitle());
        dto.setContext(model.getContext());
        dto.setLastUpdate(model.getLastUpdate());
        return abstractMapper.completeAbstractDto(model, dto);
    }

    public Page<ChatResponseDto> toDto(Page<ChatModel> page) {
        return new PageImpl<>(
                page.getContent().stream()
                        .map(this::toDto)
                        .collect(Collectors.toList()),
                page.getPageable(),
                page.getTotalElements()
        );
    }
}
