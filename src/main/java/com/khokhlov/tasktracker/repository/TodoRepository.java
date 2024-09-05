package com.khokhlov.tasktracker.repository;

import com.khokhlov.tasktracker.model.entity.Todo;
import org.hibernate.Session;

import java.util.List;
import java.util.Optional;

public class TodoRepository implements Repository<Todo, Long> {
    @Override
    public Optional<Todo> findById(Long aLong) {
        return Optional.empty();
    }

    public Optional<Todo> findById(Long id, Session session) {
        return session.createQuery("from Todo where id = :id", Todo.class)
                .setParameter("id", id)
                .uniqueResultOptional();
    }

    @Override
    public List<Todo> findAll() {
        return List.of();
    }

    public List<Todo> findAll(Session session) {
        return session.createQuery("from Todo", Todo.class)
                .list();
    }

    @Override
    public void save(Todo todo) {

    }

    public void save(Todo todo, Session session) {
        session.persist(todo);
    }

    @Override
    public void delete(Todo todo) {

    }

    public void delete(Todo todo, Session session) {
        session.remove(todo);
    }

    @Override
    public void deleteById(Long aLong) {

    }

    public void deleteById(Long id, Session session) {
        session.createNativeQuery("DELETE FROM todos WHERE id = :id")
                .setParameter("id", id)
                .executeUpdate();
    }

    @Override
    public Todo update(Todo todo) {
        return null;
    }

    public Todo update(Todo todo, Session session) {
        return session.merge(todo);
    }
}