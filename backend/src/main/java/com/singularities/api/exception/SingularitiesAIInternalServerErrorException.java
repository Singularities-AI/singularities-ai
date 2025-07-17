package com.singularities.api.exception;

import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AccountStatusException;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
public class SingularitiesAIInternalServerErrorException extends AccountStatusException {

    public SingularitiesAIInternalServerErrorException(String msg) {
        super(msg);
    }
}
