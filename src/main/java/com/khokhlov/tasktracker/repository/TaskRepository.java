package com.khokhlov.tasktracker.repository;

import com.khokhlov.tasktracker.model.entity.Task;
import org.hibernate.Session;

import java.util.List;
import java.util.Optional;

public class TaskRepository implements Repository<Task, Long> {
    @Override
    public Optional<Task> findById(Long aLong) {
        return Optional.empty();
    }

    public Optional<Task> findById(Long id, Session session) {
        return session.createQuery("from Task where id = :id", Task.class)
                .setParameter("id", id)
                .uniqueResultOptional();
    }

    @Override
    public List<Task> findAll() {
        return List.of();
    }

//    public List<Todo> findAll(Session session, String username) {
//        return session.createQuery("from Todo t join fetch t.user where t.username = :username", Todo.class)
//                .setParameter("username", username)
//                .list();
//    }

    public List<Task> findAll(Session session, String username) {
        return session.createQuery("from Task t join fetch t.user where t.user.username = :username", Task.class)
                .setParameter("username", username)
                .list();
    }

    @Override
    public void save(Task task) {

    }

    public void save(Task task, Session session) {
        session.persist(task);
    }

    @Override
    public void delete(Task task) {

    }

    public void delete(Task task, Session session) {
        session.remove(task);
    }

    @Override
    public void deleteById(Long aLong) {

    }

    public void deleteById(Long id, Session session) {
        session.createNativeQuery("DELETE FROM tasks WHERE id = :id")
                .setParameter("id", id)
                .executeUpdate();
    }

    @Override
    public Task update(Task task) {
        return null;
    }

    public Task update(Task task, Session session) {
        return session.merge(task);
    }
}