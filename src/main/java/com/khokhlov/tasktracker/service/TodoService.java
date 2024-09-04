package com.khokhlov.tasktracker.service;

import com.khokhlov.tasktracker.mapper.TodoMapper;
import com.khokhlov.tasktracker.model.dto.TodoDTO;
import com.khokhlov.tasktracker.model.entity.Todo;
import com.khokhlov.tasktracker.repository.TodoRepository;
import lombok.RequiredArgsConstructor;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;

import java.util.List;
import java.util.Optional;

@RequiredArgsConstructor
public class TodoService {
    private final SessionFactory sessionFactory;
    private final TodoRepository todoRepository;
    private final TodoMapper todoMapper;


    public TodoDTO getTodoById(Long id) {
        try(Session session = sessionFactory.openSession()) {
            return todoMapper.toDto(todoRepository.findById(id, session).get());
        }
    }

    public List<TodoDTO> getAllTodo() {
        try (Session session = sessionFactory.openSession()) {
            return todoRepository.findAll(session)
                    .stream()
                    .map(todoMapper::toDto)
                    .toList();
        }
    }

    public void saveTodo(TodoDTO todoDTO) {
        Transaction transaction = null;
        try (Session session = sessionFactory.openSession()) {
            transaction = session.beginTransaction();
            Todo todo = todoMapper.toEntity(todoDTO);
            todoRepository.save(todo, session);
            transaction.commit();
        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();
            }
        }
    }

    public void deleteTodo(TodoDTO todoDTO) {
        Transaction transaction = null;
        try (Session session = sessionFactory.openSession()) {
            transaction = session.beginTransaction();
            Todo todo = todoMapper.toEntity(todoDTO);
            todoRepository.delete(todo, session);
            transaction.commit();
        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();
            }
        }
    }

    public void deleteTodoById(Long id) {
        Transaction transaction = null;
        try (Session session = sessionFactory.openSession()) {
            transaction = session.beginTransaction();
            todoRepository.deleteById(id, session);
            transaction.commit();
        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();
            }
        }
    }

    public void updateTodo(TodoDTO todoDTO) {
        Transaction transaction = null;
        try (Session session = sessionFactory.openSession()) {
            transaction = session.beginTransaction();
            Todo todo = todoMapper.toEntity(todoDTO);
            todoRepository.update(todo, session);
            transaction.commit();
        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();
            }
        }
    }
}