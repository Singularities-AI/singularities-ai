package com.singularities.api.dto.response;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.singularities.api.dto.AbstractDto;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@JsonIgnoreProperties(ignoreUnknown = true)
public class ModelResponseDto extends AbstractDto {

    private String name;
    private String description;
    private boolean isDownloading;
    private boolean isDownload;
    private boolean isDefault;
}
