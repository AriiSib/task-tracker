package com.khokhlov.tasktracker.repository;

import com.khokhlov.tasktracker.model.command.LoginCommand;
import com.khokhlov.tasktracker.model.entity.User;
import org.hibernate.Session;
import org.hibernate.query.Query;

import java.util.List;
import java.util.Optional;

public class UserRepository implements Repository<User, Long> {

    public User authentication(LoginCommand login, Session session) {
        String hql = "FROM User WHERE username = :username and password = :password";
        Query<User> query = session.createQuery(hql, User.class);
        query.setParameter("username", login.getUsername());
        query.setParameter("password", login.getPassword());

        return query.uniqueResult();
    }

    public Optional<User> findByUsername(String username, Session session) {
        return session.createQuery("from User where username = :username", User.class)
                .setParameter("username", username)
                .uniqueResultOptional();
    }
}