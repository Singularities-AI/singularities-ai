package com.singularities.api.mapper;

import com.singularities.api.data.entity.AbstractModel;
import com.singularities.api.dto.AbstractDto;
import org.springframework.stereotype.Component;

@Component
public class AbstractMapper<T extends AbstractModel, S extends AbstractDto> {

    public S completeAbstractDto(T model, S dto) {
        dto.setCreationDate(model.getCreationDate());
        dto.setLastUpdate(model.getLastUpdate());
        dto.setId(model.getId());
        return dto;
    }

    public T completeAbstractModel(S dto, T model) {
        model.setCreationDate(dto.getCreationDate());
        model.setLastUpdate(dto.getLastUpdate());
        model.setId(dto.getId());
        return model;
    }
}
