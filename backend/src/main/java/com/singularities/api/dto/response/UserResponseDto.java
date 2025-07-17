package com.singularities.api.dto.response;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.singularities.api.dto.AbstractDto;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.List;

@Getter
@Setter
@JsonIgnoreProperties(ignoreUnknown = true)
public class UserResponseDto extends AbstractDto {

    private String email;
    private LocalDateTime lastLogin;
    private String context;
    private List<String> roles;
}
