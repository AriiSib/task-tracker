package com.khokhlov.tasktracker.repository;

import com.khokhlov.tasktracker.model.entity.Login;
import org.hibernate.Session;
import org.hibernate.query.Query;

public class LoginRepository {

    public boolean validateUser(Login login, Session session) {
        String hql = "SELECT username FROM User WHERE username = :username and password = :password";
        Query<String> query = session.createQuery(hql, String.class);
        query.setParameter("username", login.getUsername());
        query.setParameter("password", login.getPassword());

        return query.uniqueResult() != null;
    }
}