package com.aitschool.user;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;

@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(Exception.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public ResponseEntity<String> handleException(Exception e) {
        String jsonResponse = "{\"error\": \"Internal Server Error\", \"message\": \"" + e.getMessage() + "\"}";
        return new ResponseEntity<>(jsonResponse, HttpStatus.INTERNAL_SERVER_ERROR);
    }
}
