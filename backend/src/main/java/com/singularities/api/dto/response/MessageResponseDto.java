package com.singularities.api.dto.response;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.UUID;

@Getter
@Setter
@JsonIgnoreProperties(ignoreUnknown = true)
public class MessageResponseDto {

    private UUID id;
    private String role;
    private String content;
    private LocalDateTime createdAt;
}
