package com.khokhlov.tasktracker.servlet;

import jakarta.servlet.ServletConfig;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;

import java.io.IOException;

@Slf4j
@WebServlet(name = "logoutServlet", value = "/logout")
public class LogoutServlet extends HttpServlet implements Servlet {

    @Override
    public void init(ServletConfig config) throws ServletException {
        super.init(config);

        log.debug("LogoutServlet initialized");
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) {
        req.getSession().invalidate();
        try {
            resp.sendRedirect(req.getContextPath() + "/login");
        } catch (IOException e) {
            log.error("An error occurred while processing /logout: {}", e.getMessage(), e);

            resp.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            sendErrorMessage(resp, e.getMessage());
        }
    }
}