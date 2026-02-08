package com.bank.exception;

public class ResourceNotFoundException extends RuntimeException {

    public ResourceNotFoundException(String message) {
        super(message);
    }

    // no args constructor
    public ResourceNotFoundException() {
        super("Resource not found");
    }

}
