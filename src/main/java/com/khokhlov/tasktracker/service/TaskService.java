package com.khokhlov.tasktracker.service;

import com.khokhlov.tasktracker.mapper.TaskMapper;
import com.khokhlov.tasktracker.model.command.TaskCommand;
import com.khokhlov.tasktracker.model.dto.TaskDTO;
import com.khokhlov.tasktracker.model.entity.Task;
import com.khokhlov.tasktracker.model.entity.User;
import com.khokhlov.tasktracker.repository.TaskRepository;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;

import java.util.List;


public class TaskService extends AbstractService<Task, TaskCommand, TaskDTO, TaskRepository> {

    public TaskService(SessionFactory sessionFactory, TaskRepository taskRepository, TaskMapper taskMapper) {
        super(taskMapper, taskRepository, sessionFactory);
    }


    public List<TaskDTO> findAllTasksByUsername(String username) {
        try (Session session = getSessionFactory().openSession()) {
            return getRepository().findAllTasksByUsername(session, username)
                    .stream()
                    .map(getMapper()::mapToDTO)
                    .toList();
        }
    }

    public TaskDTO saveTask(TaskCommand taskCommand, User user) {
        Transaction transaction = null;
        try (Session session = getSessionFactory().openSession()) {
            transaction = session.beginTransaction();

            if (taskCommand.getTitle().length() > 30) {
                throw new IllegalArgumentException("Task title length must be less 30");
            } else if (taskCommand.getDescription().length() > 90) {
                throw new IllegalArgumentException("Task description length must be less 90 characters");
            } else if (taskCommand.getTags().isEmpty()) {
                throw new IllegalArgumentException("Task must contain at least one tag");
            }

            Task task = getMapper().mapToEntity(taskCommand);
            task.setUser(user);
            getRepository().save(task, session);
            transaction.commit();

            return getMapper().mapToDTO(task);
        } catch (Exception e) {
            try {
                if (transaction != null) {
                    transaction.rollback();
                }
            } catch (Exception rollbackException) {
                rollbackException.printStackTrace();
            }
            throw e;
        }
    }

    public TaskDTO update(TaskCommand taskCommand) {
        Transaction transaction = null;
        try (Session session = getSessionFactory().openSession()) {
            transaction = session.beginTransaction();

            TaskMapper mapper = (TaskMapper) getMapper();

            Task existingTask = getRepository().findById(session, taskCommand.getId()).orElse(null);
            mapper.partialUpdate(taskCommand, existingTask);
            getRepository().update(existingTask, session);
            transaction.commit();

            return mapper.mapToDTO(existingTask);
        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();
            }
            return null;
        }
    }


    public TaskDTO getTaskById(Long id) {
        try (Session session = getSessionFactory().openSession()) {
            return getMapper().mapToDTO(getRepository().findById(session, id).orElseThrow(
                    () -> new RuntimeException("Task not found with id: " + id)));
        }
    }
}