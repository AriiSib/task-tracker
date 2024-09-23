package com.khokhlov.tasktracker.servlet;

import com.khokhlov.tasktracker.model.dto.TaskDTO;
import com.khokhlov.tasktracker.service.TaskService;
import jakarta.servlet.ServletConfig;
import jakarta.servlet.ServletContext;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.HashSet;
import java.util.List;

import static com.khokhlov.tasktracker.consts.Consts.TASK_SERVICE;

@WebServlet(name = "taskServlet", value = "/")
public class TaskServlet extends HttpServlet {
    private TaskService taskService;


    @Override
    public void init(ServletConfig config) throws ServletException {
        super.init(config);
        ServletContext context = config.getServletContext();
        taskService = (TaskService) context.getAttribute(TASK_SERVICE);
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String action = req.getServletPath();

        try {
            switch (action) {
                case "/new":
                    showNewForm(req, resp);
                    break;
                case "/save":
                    saveTask(req, resp);
                    break;
                case "/delete":
                    deleteTask(req, resp);
                    break;
                case "/edit":
                    showEditForm(req, resp);
                    break;
                case "/update":
                    updateTask(req, resp);
                    break;
                case "/list":
                    listTask(req, resp);
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
        doGet(req, resp);
    }


    private void listTask(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        List<TaskDTO> listTask = taskService.getAllTask(req.getSession().getAttribute("username").toString());
        req.setAttribute("listTask", listTask);
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
        String title = req.getParameter("title");
        String username = req.getSession().getAttribute("username").toString();
        String description = req.getParameter("description");
        boolean isDone = Boolean.parseBoolean(req.getParameter("isDone"));

        DateTimeFormatter df = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        LocalDate targetDate = LocalDate.parse(req.getParameter("targetDate"), df);

        TaskDTO newTask = new TaskDTO(null, title, description, targetDate, isDone, new HashSet<>(), new HashSet<>());

        taskService.saveTask(newTask, username);

        resp.sendRedirect(req.getContextPath() + "/list");
    }

    private void updateTask(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        Long id = Long.parseLong(req.getParameter("id"));

        String title = req.getParameter("title");
        String username = req.getSession().getAttribute("username").toString();
        String description = req.getParameter("description");
        boolean isDone = Boolean.parseBoolean(req.getParameter("isDone"));


        DateTimeFormatter df = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        LocalDate targetDate = LocalDate.parse(req.getParameter("targetDate"), df);

        TaskDTO updateTask = new TaskDTO(id, title, description, targetDate, isDone, new HashSet<>(), new HashSet<>());

        taskService.updateTask(updateTask, username);

        resp.sendRedirect(req.getContextPath() + "/list");
    }

    private void deleteTask(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        Long id = Long.parseLong(req.getParameter("id"));
        taskService.deleteTaskById(id);
        resp.sendRedirect(req.getContextPath() + "/list");
    }



}