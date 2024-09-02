package com.khokhlov.tasktracker.servlet;

import java.io.*;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.khokhlov.tasktracker.service.TagService;
import com.khokhlov.tasktracker.service.UserService;
import jakarta.servlet.ServletConfig;
import jakarta.servlet.ServletContext;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.*;
import jakarta.servlet.annotation.*;

@WebServlet(name = "tagApiServlet", value = "/api/tags")
public class TagApiServlet extends HttpServlet implements Servlet {
    private String message;
    private UserService userService;
    private TagService tagService;
    private ObjectMapper objectMapper;

    @Override
    public void init(ServletConfig config) throws ServletException {
        super.init(config);
        ServletContext context = config.getServletContext();
        userService = (UserService) context.getAttribute("userService");
        tagService = (TagService) context.getAttribute("tagService");
        objectMapper = (ObjectMapper) context.getAttribute("objectMapper");
    }

    public void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
        sendObjectAsJson(objectMapper, response, tagService.getAllTags());
    }
}