package com.khokhlov.tasktracker.servlet;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.khokhlov.tasktracker.model.command.TaskCommand;
import com.khokhlov.tasktracker.model.dto.TaskDTO;
import com.khokhlov.tasktracker.model.entity.User;
import com.khokhlov.tasktracker.service.TaskService;
import jakarta.servlet.ServletConfig;
import jakarta.servlet.ServletContext;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.util.List;

import static com.khokhlov.tasktracker.consts.Consts.OBJECT_MAPPER;
import static com.khokhlov.tasktracker.consts.Consts.TASK_SERVICE;

@WebServlet(name = "taskServlet", value = "/")
public class TaskServlet extends HttpServlet implements Servlet {
    private ObjectMapper objectMapper;
    private transient TaskService taskService;
    private User user;


    @Override
    public void init(ServletConfig config) throws ServletException {
        super.init(config);
        ServletContext context = config.getServletContext();
        taskService = (TaskService) context.getAttribute(TASK_SERVICE);
        objectMapper = (ObjectMapper) context.getAttribute(OBJECT_MAPPER);
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String action = req.getServletPath();
        try {
            switch (action) {
                case "/edit":
                    showEditForm(req, resp);
                    break;
                case "/list":
                    showTaskList(req, resp);
                    break;
                case "/log":
                    user = (User) req.getSession().getAttribute("user");
                    showTaskList(req, resp);
                    break;
                default:
                    req.getRequestDispatcher(req.getContextPath() + "/login.jsp").forward(req, resp);
                    break;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String action = req.getServletPath();
        try {
            switch (action) {
                case "/new":
                    showNewForm(req, resp);
                    break;
                case "/save":
                    saveTask(req, resp);
                    break;
                default:
                    resp.sendRedirect(req.getContextPath() + "/list");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    protected void doPut(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        try {
            updateTask(req, resp);
        } catch (IOException e) {
            e.printStackTrace();
        }
        resp.setStatus(HttpServletResponse.SC_OK);
    }

    @Override
    protected void doDelete(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        try {
            deleteTask(req, resp);
        } catch (IOException e) {
            e.printStackTrace();
        }
        resp.setStatus(HttpServletResponse.SC_OK);
    }

    private void showTaskList(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        List<TaskDTO> taskList = taskService.findAllTasksByUsername(user.getUsername());
        req.setAttribute("taskList", taskList);
        req.getRequestDispatcher(req.getContextPath() + "/task-list.jsp").forward(req, resp);
    }

    private void showNewForm(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.getRequestDispatcher(req.getContextPath() + "/task-form.jsp").forward(req, resp);
    }

    private void showEditForm(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        Long id = Long.parseLong(req.getParameter("id"));
        TaskDTO existingTask = taskService.getTaskById(id);
        req.setAttribute("task", existingTask);
        req.getRequestDispatcher(req.getContextPath() + "/task-form.jsp").forward(req, resp);
    }

    private void saveTask(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        TaskCommand taskCommand = getObjectFromBody(objectMapper, req, TaskCommand.class);
        taskService.saveTask(taskCommand, user);
    }

    private void updateTask(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        TaskCommand taskCommand = getObjectFromBody(objectMapper, req, TaskCommand.class);
        taskService.update(taskCommand);
    }

    private void deleteTask(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        TaskCommand taskCommand = getObjectFromBody(objectMapper, req, TaskCommand.class);
        taskService.deleteById(taskCommand.getId());
    }
}