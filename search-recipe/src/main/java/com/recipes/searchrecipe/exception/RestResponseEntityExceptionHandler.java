package com.recipes.searchrecipe.exception;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.util.HashMap;

@ControllerAdvice
public class RestResponseEntityExceptionHandler extends ResponseEntityExceptionHandler {


    @ExceptionHandler(InvalidDataException.class)
    protected ResponseEntity<Object> handleConflict(
            InvalidDataException ex, WebRequest request) {

        return handleExceptionInternal(ex, "Invalid  input for : "+ ex.getMessage(),
                new HttpHeaders(), HttpStatus.CONFLICT, request);
    }
}