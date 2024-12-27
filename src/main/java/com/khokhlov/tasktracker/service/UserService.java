package com.khokhlov.tasktracker.service;

import com.khokhlov.tasktracker.mapper.UserMapper;
import com.khokhlov.tasktracker.model.command.LoginCommand;
import com.khokhlov.tasktracker.model.command.UserCommand;
import com.khokhlov.tasktracker.model.dto.UserDTO;
import com.khokhlov.tasktracker.model.entity.User;
import com.khokhlov.tasktracker.model.utils.Validator;
import com.khokhlov.tasktracker.repository.UserRepository;
import org.hibernate.Session;
import org.hibernate.SessionFactory;

import java.util.Optional;


public class UserService extends AbstractService<User, UserCommand, UserDTO, UserRepository> {

    public UserService(SessionFactory sessionFactory, UserRepository userRepository, UserMapper userMapper) {
        super(userMapper, userRepository, sessionFactory);
    }

    public User authenticate(LoginCommand login) {
        User validUser = null;
        try (Session session = getSessionFactory().openSession()) {
            validUser = getRepository().authentication(login, session);

            Validator.validateAuthenticate(validUser);
        }
        return validUser;
    }

    @Override
    public UserDTO save(UserCommand userCommand) {
        try (Session session = getSessionFactory().openSession()) {
            Validator.validateFirstName(userCommand.getFirstName());
            Validator.validateLastName(userCommand.getLastName());
            Validator.validateUsernameLength(userCommand.getUsername());
            Validator.validatePasswordLength(userCommand.getPassword());

            Optional<User> existingUser = getRepository().findByUsername(userCommand.getUsername(), session);
            Validator.validateUserExisting(existingUser);
        }
        return super.save(userCommand);
    }
}