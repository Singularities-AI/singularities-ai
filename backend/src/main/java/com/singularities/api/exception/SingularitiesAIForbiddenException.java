package com.singularities.api.exception;

import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AccountStatusException;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.FORBIDDEN)
public class SingularitiesAIForbiddenException extends AccountStatusException {

    public SingularitiesAIForbiddenException(String message) {
        super(message);
    }
}
