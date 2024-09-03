package com.khokhlov.tasktracker.servlet;

import java.io.*;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.khokhlov.tasktracker.service.TagService;
import jakarta.servlet.ServletConfig;
import jakarta.servlet.ServletContext;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.*;
import jakarta.servlet.annotation.*;

import static com.khokhlov.tasktracker.consts.Consts.*;


@WebServlet(name = "tagApiServlet", value = "/api/tags")
public class TagApiServlet extends HttpServlet implements Servlet {
    private TagService tagService;
    private ObjectMapper objectMapper;

    @Override
    public void init(ServletConfig config) throws ServletException {
        super.init(config);
        ServletContext context = config.getServletContext();
        tagService = (TagService) context.getAttribute(TAG_SERVICE);
        objectMapper = (ObjectMapper) context.getAttribute(OBJECT_MAPPER);
    }

    public void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
        sendObjectAsJson(objectMapper, response, tagService.getAllTags());
    }
}