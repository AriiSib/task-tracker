package com.khokhlov.tasktracker.exception;

public class InvalidLoginOrPassword extends RuntimeException {
    public InvalidLoginOrPassword(String message) {
        super(message);
    }
}
