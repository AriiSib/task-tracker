package com.khokhlov.tasktracker.exception;

public class SendJsonException extends RuntimeException {
    public SendJsonException(String message) {
        super(message);
    }
}