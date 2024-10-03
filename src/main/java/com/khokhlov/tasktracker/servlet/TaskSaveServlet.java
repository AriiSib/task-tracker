package com.khokhlov.tasktracker.servlet;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.khokhlov.tasktracker.model.command.TaskCommand;
import com.khokhlov.tasktracker.model.entity.User;
import com.khokhlov.tasktracker.service.TaskService;
import jakarta.servlet.ServletConfig;
import jakarta.servlet.ServletContext;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;


import static com.khokhlov.tasktracker.consts.Consts.OBJECT_MAPPER;
import static com.khokhlov.tasktracker.consts.Consts.TASK_SERVICE;

@Slf4j
@WebServlet(name = "taskSaveServlet", value = "/save")
public class TaskSaveServlet extends HttpServlet implements Servlet {
    private ObjectMapper objectMapper;
    private transient TaskService taskService;


    @Override
    public void init(ServletConfig config) throws ServletException {
        super.init(config);
        ServletContext context = config.getServletContext();
        taskService = (TaskService) context.getAttribute(TASK_SERVICE);
        objectMapper = (ObjectMapper) context.getAttribute(OBJECT_MAPPER);

        log.debug("TaskSaveServlet initialized");
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) {
        saveTask(req, resp);
    }

    private void saveTask(HttpServletRequest req, HttpServletResponse resp) {
        User user = (User) req.getSession().getAttribute("user");
        TaskCommand taskCommand = getObjectFromBody(objectMapper, req, TaskCommand.class);
        try {
            taskService.saveTask(taskCommand, user);
        } catch (Exception e) {
            log.debug("Failed to save task: {}", e.getMessage(), e);

            resp.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            sendErrorMessage(resp, e.getMessage());
        }
    }
}