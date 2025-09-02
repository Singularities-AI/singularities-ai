package com.singularities.api.dto.request;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

import java.util.UUID;

@Getter
@Setter
@JsonIgnoreProperties(ignoreUnknown = true)
public class AgentRequestDto {

    private UUID modelUUID;
    private String icon;

    @Size(min = 1, max = 45)
    private String name;

    @Size(min = 1, max = 240)
    private String description;
    private String prompt;
}
