package com.khokhlov.tasktracker.model.dto;

import lombok.Value;

import java.io.Serializable;
import java.time.LocalDate;

/**
 * DTO for {@link com.khokhlov.tasktracker.model.entity.Todo}
 */
@Value
public class TodoDTO implements Serializable {
    Long id;
    String title;
    String username;
    String description;
    LocalDate targetDate;
    boolean status;
}