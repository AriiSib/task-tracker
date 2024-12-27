package com.khokhlov.tasktracker.repository;

import com.khokhlov.tasktracker.model.entity.Task;
import org.hibernate.Session;

import java.util.List;

public class TaskRepository implements Repository<Task, Long> {

    public List<Task> findAllTasksByUsername(Session session, String username) {
        return session.createQuery("from Task t join fetch t.user where t.user.username = :username", Task.class)
                .setParameter("username", username)
                .list();
    }
}