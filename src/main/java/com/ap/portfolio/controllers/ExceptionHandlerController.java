package com.ap.portfolio.controllers;

import com.ap.portfolio.utilities.Message;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.exc.InvalidFormatException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class ExceptionHandlerController {

    private final ObjectMapper objectMapper;

    public ExceptionHandlerController(ObjectMapper objectMapper) {
        this.objectMapper = objectMapper;
        this.objectMapper.configure(DeserializationFeature.FAIL_ON_MISSING_EXTERNAL_TYPE_ID_PROPERTY, true);
    }

    @ExceptionHandler(HttpMessageNotReadableException.class)
    public ResponseEntity<Message> handleInvalidJson(HttpMessageNotReadableException ex) {
        Throwable cause = ex.getCause();
        if (cause instanceof InvalidFormatException) {
            InvalidFormatException ife = (InvalidFormatException) cause;
            if (!ife.getTargetType().equals(Double.class)) {
                return new ResponseEntity<>(new Message("Percentage must be a number"), HttpStatus.BAD_REQUEST);
            }
        }
        return new ResponseEntity<>(new Message("Invalid JSON request body"), HttpStatus.BAD_REQUEST);
    }

}

