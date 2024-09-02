package com.khokhlov.tasktracker.model.dto;

import lombok.Value;

import java.io.Serializable;

/**
 * DTO for {@link com.khokhlov.tasktracker.model.entity.User}
 */
@Value
public class UserDTO implements Serializable {
    String firstName;
    String lastName;
    String username;
    String password;
}