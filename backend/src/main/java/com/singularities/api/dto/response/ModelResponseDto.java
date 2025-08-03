package com.singularities.api.dto.response;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Getter;
import lombok.Setter;

import java.util.UUID;

@Getter
@Setter
@JsonIgnoreProperties(ignoreUnknown = true)
public class ModelResponseDto {

    private UUID id;
    private String name;
    private String description;
    private boolean isDownloading;
    private boolean isDownload;
    private boolean isDefault;
}
