package com.singularities.api.mapper;

import com.singularities.api.data.entity.SettingModel;
import com.singularities.api.data.entity.SettingValueModel;
import com.singularities.api.dto.response.SettingResponseDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class SettingMapper {

    private final AbstractMapper<SettingModel, SettingResponseDto> abstractMapper;

    public SettingResponseDto toDto(SettingModel model) {
        SettingResponseDto dto = new SettingResponseDto();
        dto.setMultiple(model.isMultiple());
        dto.setKey(model.getKey().name());
        dto.setValues(model.getValues().stream().map(SettingValueModel::getValue).toList());
        return abstractMapper.completeAbstractDto(model, dto);
    }
}
