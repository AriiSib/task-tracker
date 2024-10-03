package com.khokhlov.tasktracker.servlet;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.khokhlov.tasktracker.model.command.TaskCommand;
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
@WebServlet(name = "taskDeleteServlet", value = "/delete")
public class TaskDeleteServlet extends HttpServlet implements Servlet {
    private ObjectMapper objectMapper;
    private transient TaskService taskService;

    @Override
    public void init(ServletConfig config) throws ServletException {
        super.init(config);
        ServletContext context = config.getServletContext();
        taskService = (TaskService) context.getAttribute(TASK_SERVICE);
        objectMapper = (ObjectMapper) context.getAttribute(OBJECT_MAPPER);

        log.debug("TaskDeleteServlet initialized");
    }


    @Override
    protected void doDelete(HttpServletRequest req, HttpServletResponse resp) {
        deleteTask(req, resp);
    }

    private void deleteTask(HttpServletRequest req, HttpServletResponse resp) {
        TaskCommand taskCommand = getObjectFromBody(objectMapper, req, TaskCommand.class);
        try {
            taskService.deleteById(taskCommand.getId());
            resp.setStatus(HttpServletResponse.SC_OK);
        } catch (Exception e) {
            log.error("Failed to delete task: {}", e.getMessage(), e);

            resp.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            sendErrorMessage(resp, e.getMessage());
        }
    }
}