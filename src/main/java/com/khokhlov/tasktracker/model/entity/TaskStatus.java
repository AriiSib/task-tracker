package com.khokhlov.tasktracker.model.entity;

public enum TaskStatus {
    NOT_STARTED,
    IN_PROGRESS,
    DONE;

    public String getName() {
        return name();
    }
}