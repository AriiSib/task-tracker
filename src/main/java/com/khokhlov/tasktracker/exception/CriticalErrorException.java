package com.khokhlov.tasktracker.exception;

public class CriticalErrorException extends RuntimeException {
    public CriticalErrorException(String message) {
        super(message);
    }
}