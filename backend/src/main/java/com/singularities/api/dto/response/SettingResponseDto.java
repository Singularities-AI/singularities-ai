package com.singularities.api.dto.response;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.singularities.api.dto.AbstractDto;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@JsonIgnoreProperties(ignoreUnknown = true)
public class SettingResponseDto extends AbstractDto {

    private String key;
    private boolean isMultiple;
    private List<String> values;
}
