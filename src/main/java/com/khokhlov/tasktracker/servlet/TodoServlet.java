package com.khokhlov.tasktracker.servlet;

import com.khokhlov.tasktracker.model.dto.TodoDTO;
import com.khokhlov.tasktracker.service.TodoService;
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
import java.util.List;

import static com.khokhlov.tasktracker.consts.Consts.TODO_SERVICE;

@WebServlet(name = "todoServlet", value = "/")
public class TodoServlet extends HttpServlet {
    private TodoService todoService;


    @Override
    public void init(ServletConfig config) throws ServletException {
        super.init(config);
        ServletContext context = config.getServletContext();
        todoService = (TodoService) context.getAttribute(TODO_SERVICE);
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
                    saveTodo(req, resp);
                    break;
                case "/delete":
                    deleteTodo(req, resp);
                    break;
                case "/edit":
                    showEditForm(req, resp);
                    break;
                case "/update":
                    updateTodo(req, resp);
                    break;
                case "/list":
                    listTodo(req, resp);
                    break;
                default:
                    req.getRequestDispatcher("/login.jsp").forward(req, resp);
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


    private void listTodo(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        List<TodoDTO> listTodo = todoService.getAllTodo();
        req.setAttribute("listTodo", listTodo);
        req.getRequestDispatcher("/todo-list.jsp").forward(req, resp);
    }

    private void showNewForm(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.getRequestDispatcher("/todo-form.jsp").forward(req, resp);
    }

    private void showEditForm(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        Long id = Long.parseLong(req.getParameter("id"));
        TodoDTO existingTodo = todoService.getTodoById(id);
        req.setAttribute("todo", existingTodo);
        req.getRequestDispatcher("/todo-form.jsp").forward(req, resp);
    }

    private void saveTodo(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        String title = req.getParameter("title");
        String username = req.getParameter("username");
        String description = req.getParameter("description");
        boolean isDone = Boolean.parseBoolean(req.getParameter("isDone"));

        DateTimeFormatter df = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        LocalDate targetDate = LocalDate.parse(req.getParameter("targetDate"), df);

        TodoDTO newTodo = new TodoDTO(null, title, username, description, targetDate, isDone);

        todoService.saveTodo(newTodo);

        resp.sendRedirect(req.getContextPath() + "/list");
    }

    private void updateTodo(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        Long id = Long.parseLong(req.getParameter("id"));

        String title = req.getParameter("title");
        String username = req.getParameter("username");
        String description = req.getParameter("description");
        boolean isDone = Boolean.parseBoolean(req.getParameter("isDone"));

        DateTimeFormatter df = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        LocalDate targetDate = LocalDate.parse(req.getParameter("targetDate"), df);

        TodoDTO updateTodo = new TodoDTO(id, title, username, description, targetDate, isDone);

        todoService.updateTodo(updateTodo);

        resp.sendRedirect(req.getContextPath() + "/list");
    }

    private void deleteTodo(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        Long id = Long.parseLong(req.getParameter("id"));
        todoService.deleteTodoById(id);
        resp.sendRedirect(req.getContextPath() + "/list");
    }

}