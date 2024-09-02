package com.khokhlov.tasktracker.repository;

import com.khokhlov.tasktracker.model.entity.User;
import org.hibernate.Session;

import java.util.List;
import java.util.Optional;

public class UserRepository implements Repository<User, Long> {

    @Override
    public Optional<User> findById(Long aLong) {
        return Optional.empty();
    }

    @Override
    public List<User> findAll() {
        return List.of();
    }

    public List<User> findAll(Session session) {
        return session.createQuery("from User", User.class)
                .list();
    }

    @Override
    public void save(User user) {

    }

    public void save(User user, Session session) {
        session.persist(user);
    }

    @Override
    public void delete(User user) {

    }

    @Override
    public void deleteById(Long aLong) {

    }

    @Override
    public User update(User user) {
        return null;
    }
}
