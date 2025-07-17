package com.singularities.api.exception;

import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AccountStatusException;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.NOT_FOUND)
public class SingularitiesAINotFoundException extends AccountStatusException {

    public SingularitiesAINotFoundException(String message) {
        super(message);
    }
}
