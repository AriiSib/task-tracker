package com.khokhlov.tasktracker.servlet;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.khokhlov.tasktracker.model.command.TagCommand;
import com.khokhlov.tasktracker.service.TagService;
import jakarta.servlet.ServletConfig;
import jakarta.servlet.ServletContext;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.*;
import jakarta.servlet.annotation.*;
import lombok.extern.slf4j.Slf4j;

import static com.khokhlov.tasktracker.consts.Consts.*;

@Slf4j
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
        log.debug("TagServlet initialized");
    }

    @Override
    public void doGet(HttpServletRequest request, HttpServletResponse response) {
        tagService.findAll();
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) {
        TagCommand tagCommand = getObjectFromBody(objectMapper, req, TagCommand.class);
        tagService.save(tagCommand);

        resp.setStatus(HttpServletResponse.SC_CREATED);
    }

    @Override
    protected void doDelete(HttpServletRequest req, HttpServletResponse resp) {
        Long tagId = null;
        try {
            tagId = Long.parseLong(req.getParameter("id"));
        } catch (NumberFormatException e) {
            log.error(e.getMessage());
            resp.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            sendErrorMessage(resp, e.getMessage());
        }

        tagService.deleteById(tagId);
        resp.setStatus(HttpServletResponse.SC_NO_CONTENT);
    }
}