package com.khokhlov.tasktracker.servlet;

import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.io.PrintWriter;

public interface Servlet {

    default void sendObjectAsJson(ObjectMapper objectMapper, HttpServletResponse response, Object object) {
        try {
            response.setContentType("application/json");
            PrintWriter out = response.getWriter();
            out.print(objectMapper.writeValueAsString(object));
            out.flush();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    default <T> T getObjectFromBody(ObjectMapper objectMapper, HttpServletRequest req, Class<T> valueType) {
        try {
            return objectMapper.readValue(req.getInputStream(), valueType);
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }
}
