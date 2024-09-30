package com.khokhlov.tasktracker.servlet;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.khokhlov.tasktracker.model.command.TagCommand;
import com.khokhlov.tasktracker.service.TagService;
import jakarta.servlet.ServletConfig;
import jakarta.servlet.ServletContext;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.*;
import jakarta.servlet.annotation.*;

import static com.khokhlov.tasktracker.consts.Consts.*;


@WebServlet(name = "tagServlet", value = "/tags")
public class TagServlet extends HttpServlet implements Servlet {
    private ObjectMapper objectMapper;
    private transient TagService tagService;

    @Override
    public void init(ServletConfig config) throws ServletException {
        super.init(config);
        ServletContext context = config.getServletContext();
        tagService = (TagService) context.getAttribute(TAG_SERVICE);
        objectMapper = (ObjectMapper) context.getAttribute(OBJECT_MAPPER);
    }

    public void doGet(HttpServletRequest request, HttpServletResponse response) {
        sendObjectAsJson(objectMapper, response, tagService.findAll());
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) {
        var tagCommand = getObjectFromBody(objectMapper, req, TagCommand.class);
        var tadDTO = tagService.save(tagCommand);

        resp.setStatus(HttpServletResponse.SC_CREATED);

        sendObjectAsJson(objectMapper, resp, tadDTO); //delete
    }
}