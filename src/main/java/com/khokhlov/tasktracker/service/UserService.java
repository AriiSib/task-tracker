package com.khokhlov.tasktracker.service;

import com.khokhlov.tasktracker.exception.InvalidLoginOrPassword;
import com.khokhlov.tasktracker.exception.UserAlreadyExistsException;
import com.khokhlov.tasktracker.mapper.UserMapper;
import com.khokhlov.tasktracker.model.command.LoginCommand;
import com.khokhlov.tasktracker.model.command.UserCommand;
import com.khokhlov.tasktracker.model.dto.UserDTO;
import com.khokhlov.tasktracker.model.entity.User;
import com.khokhlov.tasktracker.repository.UserRepository;
import org.hibernate.Session;
import org.hibernate.SessionFactory;


public class UserService extends AbstractService<User, UserCommand, UserDTO, UserRepository> {

    public UserService(SessionFactory sessionFactory, UserRepository userRepository, UserMapper userMapper) {
        super(userMapper, userRepository, sessionFactory);
    }

    public User authenticate(LoginCommand login) {
        User validUser = null;
        try (Session session = getSessionFactory().openSession()) {
            validUser = getRepository().authentication(login, session);
            if (validUser == null) {
                throw new InvalidLoginOrPassword("Invalid login or password");
            }
        }
        return validUser;
    }

    @Override
    public UserDTO save(UserCommand userCommand) {
        try (Session session = getSessionFactory().openSession()) {
            var existingUser = getRepository().findByUsername(userCommand.getUsername(), session);
            if (existingUser.isPresent()) {
                throw new UserAlreadyExistsException("That username already taken");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return super.save(userCommand);
    }
}
