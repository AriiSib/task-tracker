package com.khokhlov.tasktracker.servlet;

import ch.qos.logback.classic.Logger;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.khokhlov.tasktracker.exception.CriticalErrorException;
import com.khokhlov.tasktracker.exception.GetObjectFromJsonException;
import com.khokhlov.tasktracker.exception.SendJsonException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.io.PrintWriter;

public interface Servlet {
    Logger log = (Logger) LoggerFactory.getLogger(Servlet.class);

    default void sendObjectAsJson(ObjectMapper objectMapper, HttpServletResponse response, Object object) {
        try {
            response.setContentType("application/json");
            PrintWriter out = response.getWriter();
            out.print(objectMapper.writeValueAsString(object));
            out.flush();
        } catch (IOException e) {
            log.error(e.getMessage(), e);

            throw new SendJsonException("Failed to send json: " + e.getMessage());
        }
    }

    default <T> T getObjectFromBody(ObjectMapper objectMapper, HttpServletRequest req, Class<T> valueType) {
        try {
            return objectMapper.readValue(req.getInputStream(), valueType);
        } catch (IOException e) {
            log.error(e.getMessage(), e);

            throw new GetObjectFromJsonException("Failed to create object from json: " + e.getMessage());
        }
    }

    default void sendErrorMessage(HttpServletResponse resp, String errorMessage) {
        try {
            resp.setContentType("application/json");
            resp.getWriter().write("{\"error\": \"" + errorMessage + "\"}");
        } catch (IOException ex) {
            log.error("Critical error! Failed to send error message: {}", ex.getMessage(), ex);

            throw new CriticalErrorException("A critical error occurred during program execution!");
        }
    }
}