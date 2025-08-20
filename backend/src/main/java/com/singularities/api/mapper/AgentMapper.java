package com.singularities.api.mapper;

import com.singularities.api.data.entity.AgentModel;
import com.singularities.api.dto.response.AgentResponseDto;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.stereotype.Service;

import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class AgentMapper {

    public AgentResponseDto toDto(AgentModel model) {
        AgentResponseDto dto = new AgentResponseDto();
        dto.setId(model.getId());
        dto.setModelUUID(model.getModel().getId());
        dto.setName(model.getName());
        dto.setDescription(model.getDescription());
        dto.setIcon(model.getIcon());
        dto.setCreatedAt(model.getCreationDate());
        return dto;
    }

    public Page<AgentResponseDto> toDto(Page<AgentModel> page) {
        return new PageImpl<>(
                page.getContent().stream()
                        .map(this::toDto)
                        .collect(Collectors.toList()),
                page.getPageable(),
                page.getTotalElements()
        );
    }
}
