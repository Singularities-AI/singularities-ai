package com.singularities.api.mapper;

import com.singularities.api.data.entity.ModelModel;
import com.singularities.api.dto.response.ModelResponseDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ModelMapper {

    public ModelResponseDto toDto(ModelModel model) {
        ModelResponseDto dto = new ModelResponseDto();
        dto.setId(model.getId());
        dto.setName(model.getName());
        dto.setDescription(model.getDescription());
        dto.setDownloading(model.isDownloading());
        dto.setDownload(model.isDownload());
        dto.setDefault(model.isDefault());
        return dto;
    }
}
