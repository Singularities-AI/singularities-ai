package com.singularities.api.dto;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.UUID;

@Getter
@Setter
public class AbstractDto {

    private UUID id;
    private LocalDateTime creationDate;
    private LocalDateTime lastUpdate;
}
