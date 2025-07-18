package com.example.exception;

public class BlankMessageException extends RuntimeException {
    public BlankMessageException(String message) {
        super(message);
    }
}
