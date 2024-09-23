package com.khokhlov.tasktracker.service;

import com.khokhlov.tasktracker.mapper.TaskMapper;
import com.khokhlov.tasktracker.model.dto.TaskDTO;
import com.khokhlov.tasktracker.model.entity.Task;
import com.khokhlov.tasktracker.repository.TaskRepository;
import lombok.RequiredArgsConstructor;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;

import java.util.List;

@RequiredArgsConstructor
public class TaskService {
    private final SessionFactory sessionFactory;
    private final TaskRepository taskRepository;
    private final UserService userService;
    private final TaskMapper taskMapper;


    public TaskDTO getTaskById(Long id) {
        try (Session session = sessionFactory.openSession()) {
            return taskMapper.toDto(taskRepository.findById(id, session).orElseThrow(
                    () -> new RuntimeException("Task not found with id: " + id)));
        }
    }

    public List<TaskDTO> getAllTask(String username) {
        try (Session session = sessionFactory.openSession()) {
            return taskRepository.findAll(session, username)
                    .stream()
                    .map(taskMapper::toDto)
                    .toList();
        }
    }

    public void saveTask(TaskDTO taskDTO, String username) {
        Transaction transaction = null;
        try (Session session = sessionFactory.openSession()) {
            transaction = session.beginTransaction();
            Task task = taskMapper.toEntity(taskDTO);
            task.setUser(userService.findByUsername(username));
            taskRepository.save(task, session);
            transaction.commit();
        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();
            }
        }
    }

    public void deleteTask(TaskDTO taskDTO) {
        Transaction transaction = null;
        try (Session session = sessionFactory.openSession()) {
            transaction = session.beginTransaction();
            Task task = taskMapper.toEntity(taskDTO);
            taskRepository.delete(task, session);
            transaction.commit();
        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();
            }
        }
    }

    public void deleteTaskById(Long id) {
        Transaction transaction = null;
        try (Session session = sessionFactory.openSession()) {
            transaction = session.beginTransaction();
            taskRepository.deleteById(id, session);
            transaction.commit();
        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();
            }
        }
    }

    public void updateTask(TaskDTO taskDTO, String username) {
        Transaction transaction = null;
        try (Session session = sessionFactory.openSession()) {
            transaction = session.beginTransaction();
            Task task = taskMapper.toEntity(taskDTO);
            task.setUser(userService.findByUsername(username));
            taskRepository.update(task, session);
            transaction.commit();
        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();
            }
        }
    }
}