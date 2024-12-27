package com.khokhlov.tasktracker.provider;

import com.khokhlov.tasktracker.model.entity.Tag;
import com.khokhlov.tasktracker.model.entity.Task;
import com.khokhlov.tasktracker.model.entity.User;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

import java.util.Properties;

@NoArgsConstructor
@AllArgsConstructor
public class TaskTrackerSessionProvider implements SessionProvider {
    private String url;
    private String username;
    private String password;

    @Override
    public SessionFactory getSessionFactory() {
        Properties hibernateProperties = new Properties();

        hibernateProperties.setProperty("hibernate.dialect", "org.hibernate.dialect.PostgreSQLDialect");
        hibernateProperties.setProperty("hibernate.connection.driver_class", "org.postgresql.Driver");
        hibernateProperties.setProperty("hibernate.connection.url", url);
        hibernateProperties.setProperty("hibernate.connection.username", username);
        hibernateProperties.setProperty("hibernate.connection.password", password);
        hibernateProperties.setProperty("hibernate.hbm2ddl.auto", "validate");
        hibernateProperties.setProperty("hibernate.show_sql", "true");
        hibernateProperties.setProperty("hibernate.highlight_sql", "true");
        hibernateProperties.setProperty("hibernate.format_sql", "true");

        return new Configuration()
                .addProperties(hibernateProperties)
                .addAnnotatedClass(User.class)
                .addAnnotatedClass(Tag.class)
                .addAnnotatedClass(Task.class)
                .buildSessionFactory();
    }
}