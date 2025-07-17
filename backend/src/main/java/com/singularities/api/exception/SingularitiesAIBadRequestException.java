package com.singularities.api.exception;

import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AccountStatusException;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.BAD_REQUEST)
public class SingularitiesAIBadRequestException extends AccountStatusException {

    public SingularitiesAIBadRequestException(String message) {
        super(message);
    }
}
