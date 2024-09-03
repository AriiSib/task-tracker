package com.khokhlov.tasktracker.service;

import com.khokhlov.tasktracker.model.entity.Login;
import com.khokhlov.tasktracker.repository.LoginRepository;
import lombok.RequiredArgsConstructor;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;

@RequiredArgsConstructor
public class LoginService {

    private final SessionFactory sessionFactory;
    private final LoginRepository loginRepository;

    public boolean validate(Login login) {
        Transaction transaction = null;
        boolean isValidUser = false;
        try (Session session = sessionFactory.openSession()) {
            transaction = session.beginTransaction();
            isValidUser = loginRepository.validateUser(login, session);
            transaction.commit();
        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();
            }
        }
        return isValidUser;
    }
}