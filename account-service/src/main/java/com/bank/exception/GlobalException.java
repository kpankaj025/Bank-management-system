package com.bank.exception;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import com.bank.dto.ErrorResponse;

@RestControllerAdvice
public class GlobalException {

    @ExceptionHandler(ResourceNotFoundException.class)
    public ResponseEntity<ErrorResponse> handleResourceNotFoundException(ResourceNotFoundException ex) {
        ErrorResponse errorResponse = new ErrorResponse(ex.getMessage(), org.springframework.http.HttpStatus.NOT_FOUND);
        return new ResponseEntity<>(errorResponse, org.springframework.http.HttpStatus.NOT_FOUND);
    }

}
