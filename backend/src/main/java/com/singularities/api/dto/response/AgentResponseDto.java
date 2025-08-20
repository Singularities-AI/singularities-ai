package com.singularities.api.dto.response;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.singularities.api.dto.AbstractDto;
import lombok.Getter;
import lombok.Setter;

import java.util.UUID;

@Getter
@Setter
@JsonIgnoreProperties(ignoreUnknown = true)
public class AgentResponseDto extends AbstractDto {

    private UUID modelUUID;
    private String icon;
    private String name;
    private String description;
}
