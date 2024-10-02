package com.khokhlov.tasktracker.model.utils;

import com.khokhlov.tasktracker.exception.InvalidLoginOrPassword;
import com.khokhlov.tasktracker.exception.InvalidPassword;
import com.khokhlov.tasktracker.exception.InvalidUsername;
import com.khokhlov.tasktracker.exception.UserAlreadyExistsException;
import com.khokhlov.tasktracker.model.entity.Tag;
import com.khokhlov.tasktracker.model.entity.User;
import lombok.experimental.UtilityClass;

import java.util.Optional;
import java.util.Set;
import java.util.regex.Pattern;

@UtilityClass
public class Validator {
    private static final String NAME_REGEX = "^[A-Za-zА-Яа-яёЁ]+([ '-][A-Za-zА-Яа-яёЁ]+)*$";

    public static void validateFirstName(String firstName) {
        if (!Pattern.matches(NAME_REGEX, firstName)) {
            throw new IllegalArgumentException("A firstName can only have letters");
        } else if (firstName.length() > 20) {
            throw new IllegalArgumentException("A firstName cannot exceed 20 characters");
        }
    }

    public static void validateLastName(String lastName) {
        if (!Pattern.matches(NAME_REGEX, lastName)) {
            throw new IllegalArgumentException("A lastName can only have letters");
        } else if (lastName.length() > 20) {
            throw new IllegalArgumentException("A lastName cannot exceed 20 characters");
        }
    }

    public static void validateUsernameLength(String name) {
        if (name.length() > 20) {
            throw new InvalidUsername("Username too long");
        }
    }

    public static void validatePasswordLength(String password) {
        if (password.length() > 16) {
            throw new InvalidPassword("Password length must be less than 16");
        }
    }

    public static void validateUserExisting(Optional<User> user) {
        if (user.isPresent()) {
            throw new UserAlreadyExistsException("That username already taken");
        }
    }

    public static void validateTaskTitle(String title) {
        if (title.length() > 30) {
            throw new IllegalArgumentException("Task title length must be less 30");
        }
    }

    public static void validateTaskDescription(String description) {
        if (description.length() > 90) {
            throw new IllegalArgumentException("Task description length must be less 90 characters");
        }
    }

    public static void validateTaskTag(Set<Tag> tags) {
        if (tags.isEmpty()) {
            throw new IllegalArgumentException("Task must contain at least one tag");
        }
    }

    public static void validateAuthenticate(User user) {
        if (user == null) {
            throw new InvalidLoginOrPassword("Invalid login or password");
        }
    }
}