package com.singularities.api.dto.response;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.singularities.api.dto.AbstractDto;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@JsonIgnoreProperties(ignoreUnknown = true)
public class ChatResponseDto extends AbstractDto {

    private String title;
    private String context;
    private ModelResponseDto model;
}
