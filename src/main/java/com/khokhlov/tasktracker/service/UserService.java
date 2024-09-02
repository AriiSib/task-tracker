package com.khokhlov.tasktracker.service;

import com.khokhlov.tasktracker.mapper.UserMapper;
import com.khokhlov.tasktracker.model.dto.UserDTO;
import com.khokhlov.tasktracker.model.entity.User;
import com.khokhlov.tasktracker.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;

import java.util.List;

@RequiredArgsConstructor
public class UserService {
    private final SessionFactory sessionFactory;
    private final UserRepository userRepository;
    private final UserMapper userMapper;

    public List<UserDTO> getAllUsers() {
        try (Session session = sessionFactory.openSession()) {
            return userRepository.findAll(session)
                    .stream()
                    .map(userMapper::toDTO)
                    .toList();
        }
    }

    public void saveUser(UserDTO userDTO) {
        Transaction transaction = null;
        try (Session session = sessionFactory.openSession()) {
            transaction = session.beginTransaction();
            User user = userMapper.toEntity(userDTO);
            userRepository.save(user, session);
            transaction.commit();
        } catch (HibernateException e) {
            transaction.rollback();
        }
    }
}
