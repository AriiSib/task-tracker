package com.khokhlov.tasktracker.exception;

public class InvalidLoginOrPasswordException extends RuntimeException {
    public InvalidLoginOrPasswordException(String message) {
        super(message);
    }
}