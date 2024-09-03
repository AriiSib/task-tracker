package com.khokhlov.tasktracker.repository;

import com.khokhlov.tasktracker.model.entity.Login;
import org.hibernate.Session;
import org.hibernate.query.Query;

public class LoginRepository {

    public boolean validateUser(Login login, Session session) {
        String hql = "FROM User WHERE username = :username and password = :password";
        Query query = session.createQuery(hql);
        query.setParameter("username", login.getUsername());
        query.setParameter("password", login.getPassword());

        return query.uniqueResult() != null;
    }
}