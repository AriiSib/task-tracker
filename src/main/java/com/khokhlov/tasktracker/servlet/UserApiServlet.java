package com.khokhlov.tasktracker.servlet;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.khokhlov.tasktracker.model.dto.UserDTO;
import com.khokhlov.tasktracker.model.entity.User;
import com.khokhlov.tasktracker.service.UserService;
import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletConfig;
import jakarta.servlet.ServletContext;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;

import static com.khokhlov.tasktracker.consts.Consts.*;

@WebServlet(name = "registerServlet", value = "/register")
public class UserApiServlet extends HttpServlet implements Servlet {
    private UserService userService;
    private ObjectMapper objectMapper;

    @Override
    public void init(ServletConfig config) throws ServletException {
        super.init(config);
        ServletContext context = config.getServletContext();
        userService = (UserService) context.getAttribute(USER_SERVICE);
        objectMapper = (ObjectMapper) context.getAttribute(OBJECT_MAPPER);
    }

    public void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
        response.sendRedirect("/register.jsp");

//        userService.registerUser(new UserDTO("Arii", "Hh", "Arii_Sib", "123456"));
//        userService.registerUser(new UserDTO("Sergey", "Nn", "Bib", "926564"));
//        userService.registerUser(new UserDTO("Anton", "Kk", "Kupreychik", "654321"));
//        userService.registerUser(new UserDTO("Igor", "Cc", "Bogdanov", "852421"));
//        userService.registerUser(new UserDTO("Elena", "Uu", "Tarasova", "475631"));
//        sendObjectAsJson(objectMapper, response, userService.getAllUsers());
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        register(req, resp);
    }

    private void register(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String firstName = req.getParameter("firstName");
        String lastName = req.getParameter("lastName");
        String username = req.getParameter("username");
        String password = req.getParameter("password");

        UserDTO userDTO = new UserDTO(firstName, lastName, username, password);

        userService.registerUser(userDTO);
        resp.setStatus(HttpServletResponse.SC_CREATED);

        req.getRequestDispatcher("register/register.jsp").forward(req, resp);
    }
}