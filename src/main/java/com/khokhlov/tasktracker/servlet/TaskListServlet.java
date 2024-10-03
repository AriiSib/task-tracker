package com.khokhlov.tasktracker.servlet;

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
import lombok.extern.slf4j.Slf4j;

import java.io.IOException;
import java.util.List;

import static com.khokhlov.tasktracker.consts.Consts.TASK_SERVICE;

@Slf4j
@WebServlet(name = "taskListServlet", value = "/list")
public class TaskListServlet extends HttpServlet implements Servlet {
    private transient TaskService taskService;

    @Override
    public void init(ServletConfig config) throws ServletException {
        super.init(config);
        ServletContext context = config.getServletContext();
        taskService = (TaskService) context.getAttribute(TASK_SERVICE);

        log.debug("TaskListServlet initialized");
    }

    protected void doGet(HttpServletRequest req, HttpServletResponse resp) {
        try {
            if ((User) req.getSession().getAttribute("user") == null) {
                resp.sendRedirect(req.getContextPath() + "/login");
            } else {
                showTaskList(req, resp);
            }
        } catch (Exception e) {
            log.error("Failed to redirect to task list: {}", e.getMessage(), e);

            resp.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            sendErrorMessage(resp, e.getMessage());
        }
    }

    private void showTaskList(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        User user = (User) req.getSession().getAttribute("user");
        List<TaskDTO> taskList = taskService.findAllTasksByUsername(user.getUsername());
        req.setAttribute("taskList", taskList);
        req.getRequestDispatcher("/WEB-INF/task-list.jsp").forward(req, resp);
    }
}