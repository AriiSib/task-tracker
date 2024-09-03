package com.khokhlov.tasktracker.listener;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.khokhlov.tasktracker.mapper.TagMapper;
import com.khokhlov.tasktracker.mapper.TodoMapper;
import com.khokhlov.tasktracker.mapper.UserMapper;
import com.khokhlov.tasktracker.provider.SessionProvider;
import com.khokhlov.tasktracker.provider.TaskTrackerSessionProvider;
import com.khokhlov.tasktracker.repository.LoginRepository;
import com.khokhlov.tasktracker.repository.TagRepository;
import com.khokhlov.tasktracker.repository.TodoRepository;
import com.khokhlov.tasktracker.repository.UserRepository;
import com.khokhlov.tasktracker.service.LoginService;
import com.khokhlov.tasktracker.service.TagService;
import com.khokhlov.tasktracker.service.TodoService;
import com.khokhlov.tasktracker.service.UserService;
import jakarta.servlet.ServletContext;
import jakarta.servlet.ServletContextEvent;
import jakarta.servlet.ServletContextListener;
import jakarta.servlet.annotation.WebListener;
import lombok.extern.java.Log;
import org.hibernate.SessionFactory;

import static com.khokhlov.tasktracker.consts.Consts.*;

@WebListener
public class AppContextListener implements ServletContextListener {
    @Override
    public void contextInitialized(ServletContextEvent sce) {
        ServletContext ctx = sce.getServletContext();

        ObjectMapper objectMapper = new ObjectMapper();


        SessionProvider sessionProvider = new TaskTrackerSessionProvider();
        SessionFactory sessionFactory = sessionProvider.getSessionFactory();

        LoginRepository loginRepository = new LoginRepository();
        UserRepository userRepository = new UserRepository();
        TodoRepository todoRepository = new TodoRepository();
        TagRepository tagRepository = new TagRepository();

        UserMapper userMapper = UserMapper.INSTANCE;
        TodoMapper todoMapper = TodoMapper.INSTANCE;
        TagMapper tagMapper = TagMapper.INSTANCE;

        LoginService loginService = new LoginService(sessionFactory, loginRepository);
        UserService userService = new UserService(sessionFactory, userRepository, userMapper);
        TodoService todoService = new TodoService(sessionFactory, todoRepository, todoMapper);
        TagService tagService = new TagService(sessionFactory, tagRepository, tagMapper);

        ctx.setAttribute(OBJECT_MAPPER, objectMapper);
        ctx.setAttribute(LOGIN_SERVICE, loginService);
        ctx.setAttribute(USER_SERVICE, userService);
        ctx.setAttribute(TODO_SERVICE, todoService);
        ctx.setAttribute(TAG_SERVICE, tagService);

        ServletContextListener.super.contextInitialized(sce);
    }
}