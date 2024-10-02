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
import lombok.extern.slf4j.Slf4j;


import static com.khokhlov.tasktracker.consts.Consts.*;

@Slf4j
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
        log.debug("RegisterServlet initialized");
    }

    @Override
    public void doGet(HttpServletRequest request, HttpServletResponse response) {
        try {
            request.getRequestDispatcher("/register.jsp").forward(request, response);
        } catch (Exception e) {
            log.error("An error occurred while processing /register.jsp: {}", e.getMessage(), e);
        }
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) {
        register(req, resp);
    }

    private void register(HttpServletRequest req, HttpServletResponse resp) {
        try {
            log.info("Registering new user");

            UserCommand userCommand = getObjectFromBody(objectMapper, req, UserCommand.class);
            userService.save(userCommand);

            req.getSession().setAttribute("NOTIFICATION", "REGISTRATION WAS SUCCESSFUL");
            resp.setStatus(HttpServletResponse.SC_CREATED);

            log.info("User {} registered successfully", userCommand.getUsername());

            resp.sendRedirect(req.getContextPath() + "/login");
        } catch (Exception e) {
            log.error("An error occurred during user registration: {}", e.getMessage(), e);

            resp.setStatus(HttpServletResponse.SC_BAD_REQUEST);

            sendErrorMessage(resp, e.getMessage());
        }
    }
}