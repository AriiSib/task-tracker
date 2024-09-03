package com.khokhlov.tasktracker.model.entity;

import lombok.Data;

import java.io.Serializable;

@Data
public class Login implements Serializable {
    private static final long serialVersionUID = 1L;

    private String username;
    private String password;
}