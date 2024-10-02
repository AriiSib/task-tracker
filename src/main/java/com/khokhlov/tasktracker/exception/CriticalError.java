package com.khokhlov.tasktracker.exception;

public class CriticalError extends RuntimeException {
    public CriticalError(String message) {
        super(message);
    }
}