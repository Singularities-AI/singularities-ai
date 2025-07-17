package com.singularities.api.dto.response;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.UUID;

@Getter
@Setter
@JsonIgnoreProperties(ignoreUnknown = true)
public class ChatResponseDto {

    private UUID id;
    private String title;
    private String context;
    private LocalDateTime lastUpdate;
    private ModelResponseDto model;
}
