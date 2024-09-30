package com.khokhlov.tasktracker.model.command;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Builder
@NoArgsConstructor
@AllArgsConstructor
@Getter
public class UserCommand implements Command {
    private String firstName;
    private String lastName;
    private String username;
    private String password;
}