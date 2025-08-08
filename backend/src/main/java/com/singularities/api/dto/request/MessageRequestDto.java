package com.singularities.api.dto.request;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.validation.constraints.NotEmpty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NonNull;
import lombok.Setter;

import java.util.UUID;

@Getter
@Setter
@AllArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
public class MessageRequestDto {

    private UUID chatUUID;

    @NonNull
    @NotEmpty
    private String content;

    @NonNull
    private UUID modelUUID;

    private String context;
}
