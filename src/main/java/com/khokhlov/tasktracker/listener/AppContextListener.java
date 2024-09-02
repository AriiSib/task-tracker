package com.khokhlov.tasktracker.listener;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.khokhlov.tasktracker.mapper.TagMapper;
import com.khokhlov.tasktracker.mapper.UserMapper;
import com.khokhlov.tasktracker.provider.SessionProvider;
import com.khokhlov.tasktracker.provider.TaskTrackerSessionProvider;
import com.khokhlov.tasktracker.repository.TagRepository;
import com.khokhlov.tasktracker.repository.UserRepository;
import com.khokhlov.tasktracker.service.TagService;
import com.khokhlov.tasktracker.service.UserService;
import jakarta.servlet.ServletContext;
import jakarta.servlet.ServletContextEvent;
import jakarta.servlet.ServletContextListener;
import jakarta.servlet.annotation.WebListener;
import org.hibernate.SessionFactory;

@WebListener
public class AppContextListener implements ServletContextListener {
    @Override
    public void contextInitialized(ServletContextEvent sce) {
        ServletContext ctx = sce.getServletContext();

        ObjectMapper objectMapper = new ObjectMapper();


        SessionProvider sessionProvider = new TaskTrackerSessionProvider();
        SessionFactory sessionFactory = sessionProvider.getSessionFactory();

        UserRepository userRepository = new UserRepository();
        TagRepository tagRepository = new TagRepository();

        UserMapper userMapper = UserMapper.INSTANCE;
        TagMapper tagMapper = TagMapper.INSTANCE;


        UserService userService = new UserService(sessionFactory, userRepository, userMapper);
        TagService tagService = new TagService(sessionFactory, tagRepository, tagMapper);

        ctx.setAttribute("objectMapper", objectMapper);
        ctx.setAttribute("userService", userService);
        ctx.setAttribute("tagService", tagService);

        ServletContextListener.super.contextInitialized(sce);
    }
}