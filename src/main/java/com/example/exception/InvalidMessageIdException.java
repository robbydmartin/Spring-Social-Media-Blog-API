package com.example.exception;

public class InvalidMessageIdException extends RuntimeException {
    public InvalidMessageIdException(String message) {
        super(message);
    }
}
