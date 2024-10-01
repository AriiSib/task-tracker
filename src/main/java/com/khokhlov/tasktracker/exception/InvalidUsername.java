package com.khokhlov.tasktracker.exception;

public class InvalidUsername extends RuntimeException {
    public InvalidUsername(String message) {
        super(message);
    }
}
