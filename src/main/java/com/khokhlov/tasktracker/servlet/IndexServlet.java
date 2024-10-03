package com.khokhlov.tasktracker.servlet;

import jakarta.servlet.ServletConfig;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;


@Slf4j
@WebServlet(name = "indexServlet", value = "/index")
public class IndexServlet extends HttpServlet implements Servlet {
    @Override
    public void init(ServletConfig config) throws ServletException {
        super.init(config);

        log.debug("IndexServlet initialized");
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) {
        try {
            req.getRequestDispatcher("/WEB-INF/index.jsp").forward(req, resp);
        } catch (Exception e) {
            log.error("Failed to get task list: {}", e.getMessage(), e);

            resp.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            sendErrorMessage(resp, e.getMessage());
        }
    }
}