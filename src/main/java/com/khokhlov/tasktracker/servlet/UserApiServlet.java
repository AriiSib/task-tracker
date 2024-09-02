package com.khokhlov.tasktracker.servlet;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.khokhlov.tasktracker.model.dto.UserDTO;
import com.khokhlov.tasktracker.service.UserService;
import jakarta.servlet.ServletConfig;
import jakarta.servlet.ServletContext;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;

@WebServlet(name = "userApiServlet", value = "/api/users")
public class UserApiServlet extends HttpServlet implements Servlet {
    private UserService userService;
    private ObjectMapper objectMapper;

    @Override
    public void init(ServletConfig config) throws ServletException {
        super.init(config);
        ServletContext context = config.getServletContext();
        userService = (UserService) context.getAttribute("userService");
        objectMapper = (ObjectMapper) context.getAttribute("objectMapper");
    }

    public void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
        userService.saveUser(new UserDTO("Arii", "Hh", "Arii_Sib", "123456"));
        userService.saveUser(new UserDTO("Sergey", "Nn", "Bib", "926564"));
        userService.saveUser(new UserDTO("Anton", "Kk", "Kupreychik", "654321"));
        userService.saveUser(new UserDTO("Igor", "Cc", "Bogdanov", "852421"));
        userService.saveUser(new UserDTO("Elena", "Uu", "Tarasova", "475631"));
        sendObjectAsJson(objectMapper, response, userService.getAllUsers());
    }
}
