package com.khokhlov.tasktracker.model.dto;

import lombok.Value;

import java.io.Serializable;

/**
 * DTO for {@link com.khokhlov.tasktracker.model.entity.Tag}
 */
@Value
public class TagDTO implements Serializable, DTO {
    Long id;
    String name;
}