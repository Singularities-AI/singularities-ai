package com.singularities.api.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.HashMap;
import java.util.Map;

@RestControllerAdvice
public class GlobalExceptionHandler {

    private static final Map<Class<? extends Exception>, HttpStatus> exceptionStatusMap = new HashMap<>();

    static {
        exceptionStatusMap.put(HttpRequestMethodNotSupportedException.class, HttpStatus.METHOD_NOT_ALLOWED);
        exceptionStatusMap.put(BadCredentialsException.class, HttpStatus.UNAUTHORIZED);

        //custom exception
        exceptionStatusMap.put(SingularitiesAINotFoundException.class, HttpStatus.NOT_FOUND);
        exceptionStatusMap.put(SingularitiesAIConflictException.class, HttpStatus.CONFLICT);
        exceptionStatusMap.put(SingularitiesAIForbiddenException.class, HttpStatus.FORBIDDEN);
        exceptionStatusMap.put(SingularitiesAIBadRequestException.class, HttpStatus.BAD_REQUEST);
        exceptionStatusMap.put(SingularitiesAIInternalServerErrorException.class, HttpStatus.INTERNAL_SERVER_ERROR);
    }


    @ExceptionHandler(Exception.class)
    public ResponseEntity<String> handleException(Exception e) {
        HttpStatus status = exceptionStatusMap.getOrDefault(e.getClass(), HttpStatus.INTERNAL_SERVER_ERROR);
        return ResponseEntity.status(status).body(e.getMessage());
    }
}
