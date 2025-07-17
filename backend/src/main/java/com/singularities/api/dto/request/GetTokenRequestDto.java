package com.singularities.api.dto.request;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@JsonIgnoreProperties(ignoreUnknown = true)
public class GetTokenRequestDto {

    @NotNull
    @NotBlank
    private String email;

    @NotNull
    @NotBlank
    private String code;
}
