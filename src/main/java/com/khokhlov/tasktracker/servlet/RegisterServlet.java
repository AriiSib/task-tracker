package com.khokhlov.tasktracker.servlet;

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

import static com.khokhlov.tasktracker.consts.Consts.*;

@WebServlet(name = "registerServlet", value = "/register")
public class RegisterServlet extends HttpServlet implements Servlet {
    private UserService userService;

    @Override
    public void init(ServletConfig config) throws ServletException {
        super.init(config);
        ServletContext context = config.getServletContext();
        userService = (UserService) context.getAttribute(USER_SERVICE);
    }

    public void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        request.getRequestDispatcher("/register.jsp").forward(request, response);
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

//        req.getRequestDispatcher("/login.jsp").forward(req, resp);
        resp.sendRedirect(req.getContextPath() +"/login");
    }
}