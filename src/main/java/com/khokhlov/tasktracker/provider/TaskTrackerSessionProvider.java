package com.khokhlov.tasktracker.provider;

import com.khokhlov.tasktracker.model.entity.Tag;
import com.khokhlov.tasktracker.model.entity.Todo;
import com.khokhlov.tasktracker.model.entity.User;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

import java.util.Properties;

public class TaskTrackerSessionProvider implements SessionProvider {
    @Override
    public SessionFactory getSessionFactory() {
        Properties hibernateProperties = new Properties();

        hibernateProperties.setProperty("hibernate.dialect", "org.hibernate.dialect.PostgreSQLDialect");
        hibernateProperties.setProperty("hibernate.connection.driver_class", "org.postgresql.Driver");
        hibernateProperties.setProperty("hibernate.connection.url", "jdbc:postgresql://localhost:5432/tracker");
        hibernateProperties.setProperty("hibernate.connection.username", "arii");
        hibernateProperties.setProperty("hibernate.connection.password", "arii");
        hibernateProperties.setProperty("hibernate.hbm2ddl.auto", "create");
        hibernateProperties.setProperty("hibernate.show_sql", "true");
        hibernateProperties.setProperty("hibernate.highlight_sql", "true");
        hibernateProperties.setProperty("hibernate.format_sql", "true");

        return new Configuration()
                .addProperties(hibernateProperties)
//                .addPackage("com/khokhlov/tasktracker/model/entity")
                .addAnnotatedClass(User.class)
                .addAnnotatedClass(Tag.class)
                .addAnnotatedClass(Todo.class)
                .buildSessionFactory();
    }
}