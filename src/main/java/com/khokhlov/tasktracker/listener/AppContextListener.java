package com.khokhlov.tasktracker.listener;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.khokhlov.tasktracker.mapper.TagMapper;
import com.khokhlov.tasktracker.mapper.TaskMapper;
import com.khokhlov.tasktracker.mapper.UserMapper;
import com.khokhlov.tasktracker.provider.SessionProvider;
import com.khokhlov.tasktracker.provider.TaskTrackerSessionProvider;
import com.khokhlov.tasktracker.repository.TagRepository;
import com.khokhlov.tasktracker.repository.TaskRepository;
import com.khokhlov.tasktracker.repository.UserRepository;
import com.khokhlov.tasktracker.service.TagService;
import com.khokhlov.tasktracker.service.TaskService;
import com.khokhlov.tasktracker.service.UserService;
import jakarta.servlet.ServletContext;
import jakarta.servlet.ServletContextEvent;
import jakarta.servlet.ServletContextListener;
import jakarta.servlet.annotation.WebListener;
import org.hibernate.SessionFactory;

import static com.khokhlov.tasktracker.consts.Consts.*;

@WebListener
public class AppContextListener implements ServletContextListener {
    @Override
    public void contextInitialized(ServletContextEvent sce) {
        ServletContext ctx = sce.getServletContext();

        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.registerModule(new JavaTimeModule());


        SessionProvider sessionProvider = new TaskTrackerSessionProvider();
        SessionFactory sessionFactory = sessionProvider.getSessionFactory();

        UserRepository userRepository = new UserRepository();
        TaskRepository taskRepository = new TaskRepository();
        TagRepository tagRepository = new TagRepository();

        UserMapper userMapper = UserMapper.INSTANCE;
        TaskMapper taskMapper = TaskMapper.INSTANCE;
        TagMapper tagMapper = TagMapper.INSTANCE;

        UserService userService = new UserService(sessionFactory, userRepository, userMapper);
        TaskService taskService = new TaskService(sessionFactory, taskRepository, taskMapper);
        TagService tagService = new TagService(sessionFactory, tagRepository, tagMapper);

        ctx.setAttribute(OBJECT_MAPPER, objectMapper);
        ctx.setAttribute(USER_SERVICE, userService);
        ctx.setAttribute(TASK_SERVICE, taskService);
        ctx.setAttribute(TAG_SERVICE, tagService);

        ServletContextListener.super.contextInitialized(sce);
    }
}