package com.khokhlov.tasktracker.servlet;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.khokhlov.tasktracker.exception.InvalidLoginOrPassword;
import com.khokhlov.tasktracker.model.command.LoginCommand;
import com.khokhlov.tasktracker.model.entity.User;
import com.khokhlov.tasktracker.service.UserService;
import jakarta.servlet.ServletConfig;
import jakarta.servlet.ServletContext;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.util.Map;

import static com.khokhlov.tasktracker.consts.Consts.*;

@WebServlet(name = "loginServlet", value = "/login")
public class LoginServlet extends HttpServlet implements Servlet {
    private ObjectMapper objectMapper;
    private transient UserService userService;


    @Override
    public void init(ServletConfig config) throws ServletException {
        super.init(config);
        ServletContext context = config.getServletContext();
        userService = (UserService) context.getAttribute(USER_SERVICE);
        objectMapper = (ObjectMapper) context.getAttribute(OBJECT_MAPPER);
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.getRequestDispatcher("login.jsp").forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        authenticate(req, resp);
    }

    private void authenticate(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        var loginCommand = getObjectFromBody(objectMapper, req, LoginCommand.class);

        try {
            User user = userService.authenticate(loginCommand);
            req.getSession().setAttribute("user", user);
            resp.setStatus(HttpServletResponse.SC_OK);

            resp.sendRedirect(req.getRequestURI() + "/log");
        } catch (InvalidLoginOrPassword e) {
            sendErrorMessage(resp, e.getMessage());
        } catch (Exception e) {
            sendErrorMessage(resp, "An unexpected error occurred.");
        }
    }

    private void sendErrorMessage(HttpServletResponse resp, String errorMessage) throws IOException {
        resp.setContentType("application/json");
        resp.setStatus(HttpServletResponse.SC_BAD_REQUEST);
        resp.getWriter().write("{\"errorMessage\": \"" + errorMessage + "\"}");
    }
}