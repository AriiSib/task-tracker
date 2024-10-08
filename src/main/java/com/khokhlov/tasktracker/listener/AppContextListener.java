package com.khokhlov.tasktracker.listener;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.khokhlov.tasktracker.mapper.TaskMapper;
import com.khokhlov.tasktracker.mapper.UserMapper;
import com.khokhlov.tasktracker.provider.SessionProvider;
import com.khokhlov.tasktracker.provider.TaskTrackerSessionProvider;
import com.khokhlov.tasktracker.repository.TaskRepository;
import com.khokhlov.tasktracker.repository.UserRepository;
import com.khokhlov.tasktracker.service.TaskService;
import com.khokhlov.tasktracker.service.UserService;
import jakarta.servlet.ServletContext;
import jakarta.servlet.ServletContextEvent;
import jakarta.servlet.ServletContextListener;
import jakarta.servlet.annotation.WebListener;
import lombok.extern.slf4j.Slf4j;
import org.hibernate.SessionFactory;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

import static com.khokhlov.tasktracker.consts.Consts.*;

@Slf4j
@WebListener
public class AppContextListener implements ServletContextListener {
    private final String url;
    private final String username;
    private final String password;

    public AppContextListener() {
        var config = loadProperties();
        this.url = loadSysEnvIfNullThenConfig("DB_URL", "url", config);
        this.username = loadSysEnvIfNullThenConfig("DB_USERNAME", "username", config);
        this.password = loadSysEnvIfNullThenConfig("DB_PASSWORD", "password", config);
    }

    @Override
    public void contextInitialized(ServletContextEvent sce) {
        ServletContext ctx = sce.getServletContext();

        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.registerModule(new JavaTimeModule());

        SessionProvider sessionProvider = new TaskTrackerSessionProvider(url, username, password);
        SessionFactory sessionFactory = sessionProvider.getSessionFactory();

        UserRepository userRepository = new UserRepository();
        TaskRepository taskRepository = new TaskRepository();

        UserMapper userMapper = UserMapper.INSTANCE;
        TaskMapper taskMapper = TaskMapper.INSTANCE;

        UserService userService = new UserService(sessionFactory, userRepository, userMapper);
        TaskService taskService = new TaskService(sessionFactory, taskRepository, taskMapper);

        ctx.setAttribute(OBJECT_MAPPER, objectMapper);
        ctx.setAttribute(USER_SERVICE, userService);
        ctx.setAttribute(TASK_SERVICE, taskService);

        ServletContextListener.super.contextInitialized(sce);

        log.debug("AppContextListener initialized");
    }

    private static String loadSysEnvIfNullThenConfig(String sysEnv, String key, Properties config) {
        String value = System.getenv(sysEnv);
        if (value == null) {
            value = config.getProperty(key);
            log.debug("Loading system environment variable " + sysEnv + " with value " + value);
        }
        return value;
    }

    private static Properties loadProperties() {
        Properties config = new Properties();
        try (InputStream in = AppContextListener.class.getClassLoader()
                .getResourceAsStream("liquibase.properties")) {
            config.load(in);
            log.debug("Properties file loaded successfully from {}", "liquibase.properties");
        } catch (IOException e) {
            log.error("An error occurred while loading properties {}", e.getMessage());
            throw new IllegalStateException(e);
        }
        return config;
    }
}