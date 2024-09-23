package com.khokhlov.tasktracker.service;

import com.khokhlov.tasktracker.model.entity.Login;
import com.khokhlov.tasktracker.repository.LoginRepository;
import lombok.RequiredArgsConstructor;
import org.hibernate.Session;
import org.hibernate.SessionFactory;

@RequiredArgsConstructor
public class LoginService {

    private final SessionFactory sessionFactory;
    private final LoginRepository loginRepository;

    public boolean validate(Login login) {
        try (Session session = sessionFactory.openSession()) {
            return loginRepository.validateUser(login, session);
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }
}