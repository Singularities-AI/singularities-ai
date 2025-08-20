package com.singularities.api.dto.request;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Getter;
import lombok.Setter;

import java.util.UUID;

@Getter
@Setter
@JsonIgnoreProperties(ignoreUnknown = true)
public class AgentRequestDto {

    private UUID modelUUID;
    private String icon;
    private String name;
    private String description;
    private String prompt;
}
