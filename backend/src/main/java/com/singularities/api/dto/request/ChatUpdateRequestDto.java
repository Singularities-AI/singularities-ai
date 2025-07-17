package com.singularities.api.dto.request;


import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Getter;
import lombok.Setter;

import java.util.UUID;

@Getter
@Setter
@JsonIgnoreProperties(ignoreUnknown = true)
public class ChatUpdateRequestDto {

    private String context;
    private UUID modelUUID;
}
