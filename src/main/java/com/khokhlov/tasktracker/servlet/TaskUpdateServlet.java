package com.khokhlov.tasktracker.servlet;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.khokhlov.tasktracker.model.command.TaskCommand;
import com.khokhlov.tasktracker.model.dto.TaskDTO;
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
@WebServlet(name = "taskUpdateServlet", value = "/update")
public class TaskUpdateServlet extends HttpServlet implements Servlet {
    private ObjectMapper objectMapper;
    private transient TaskService taskService;


    @Override
    public void init(ServletConfig config) throws ServletException {
        super.init(config);
        ServletContext context = config.getServletContext();
        taskService = (TaskService) context.getAttribute(TASK_SERVICE);
        objectMapper = (ObjectMapper) context.getAttribute(OBJECT_MAPPER);

        log.debug("TaskUpdateServlet initialized");
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) {
        showEditForm(req, resp);
    }

    @Override
    protected void doPut(HttpServletRequest req, HttpServletResponse resp) {
        updateTask(req, resp);
    }

    private void showEditForm(HttpServletRequest req, HttpServletResponse resp) {
        TaskDTO existingTask = null;
        try {
            Long id = Long.parseLong(req.getParameter("id"));
            existingTask = taskService.getTaskById(id);
            req.setAttribute("task", existingTask);
            req.setAttribute("tagList", existingTask.getTags());
            req.getRequestDispatcher("/WEB-INF/task-form.jsp").forward(req, resp);
        } catch (Exception e) {
            log.error("Failed to get task by ID: {}", e.getMessage(), e);

            resp.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            sendErrorMessage(resp, e.getMessage());
        }
    }

    private void updateTask(HttpServletRequest req, HttpServletResponse resp) {
        TaskCommand taskCommand = getObjectFromBody(objectMapper, req, TaskCommand.class);
        try {
            taskService.update(taskCommand);
            resp.setStatus(HttpServletResponse.SC_OK);
        } catch (Exception e) {
            log.debug("Failed to update task: {}", e.getMessage(), e);
            resp.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            sendErrorMessage(resp, e.getMessage());
        }
    }
}