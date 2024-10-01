package com.khokhlov.tasktracker.servlet;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.khokhlov.tasktracker.model.command.UserCommand;
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
    private ObjectMapper objectMapper = new ObjectMapper();
    private transient UserService userService;

    @Override
    public void init(ServletConfig config) throws ServletException {
        super.init(config);
        ServletContext context = config.getServletContext();
        userService = (UserService) context.getAttribute(USER_SERVICE);
        objectMapper = (ObjectMapper) context.getAttribute(OBJECT_MAPPER);
    }

    public void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        request.getRequestDispatcher("/register.jsp").forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException, ServletException {
        register(req, resp);
    }

    private void register(HttpServletRequest req, HttpServletResponse resp) throws IOException, ServletException {
        try {
            UserCommand userCommand = getObjectFromBody(objectMapper, req, UserCommand.class);
            userService.save(userCommand);

            req.getSession().setAttribute("NOTIFICATION", "REGISTRATION WAS SUCCESSFUL");
            resp.setStatus(HttpServletResponse.SC_CREATED);

            resp.sendRedirect(req.getContextPath() + "/login");
        } catch (Exception e) {
            resp.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            resp.setContentType("application/json");
            resp.getWriter().write("{\"error\": \"" + e.getMessage() + "\"}");
        }
    }
}