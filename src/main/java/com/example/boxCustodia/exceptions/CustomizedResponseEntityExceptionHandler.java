package com.example.boxCustodia.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@RestControllerAdvice
public class CustomizedResponseEntityExceptionHandler extends ResponseEntityExceptionHandler {

    @ExceptionHandler(ErrorParametro.class)
    @ResponseBody
    public ResponseEntity<ErrorPersonalizado> handleCustomException(ErrorParametro ex) {
        ErrorPersonalizado error = ex.getErrorPersonalizado();
        return new ResponseEntity<>(error, HttpStatus.valueOf(error.getStatus()));
    }
}
