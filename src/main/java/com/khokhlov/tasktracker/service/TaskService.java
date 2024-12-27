package com.khokhlov.tasktracker.service;

import com.khokhlov.tasktracker.exception.TaskNotFoundException;
import com.khokhlov.tasktracker.mapper.TaskMapper;
import com.khokhlov.tasktracker.model.command.TaskCommand;
import com.khokhlov.tasktracker.model.dto.TaskDTO;
import com.khokhlov.tasktracker.model.entity.Task;
import com.khokhlov.tasktracker.model.entity.User;
import com.khokhlov.tasktracker.model.utils.Validator;
import com.khokhlov.tasktracker.repository.TaskRepository;
import lombok.extern.slf4j.Slf4j;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;

import java.util.List;

@Slf4j
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

            Validator.validateTaskTitle(taskCommand.getTitle());
            Validator.validateTaskDescription(taskCommand.getDescription());
            Validator.validateTaskTag(taskCommand.getTags());


            Task task = getMapper().mapToEntity(taskCommand);
            task.setUser(user);
            getRepository().save(task, session);
            transaction.commit();

            return getMapper().mapToDTO(task);
        } catch (Exception e) {
            log.error(e.getMessage(), e);
            try {
                if (transaction != null) {
                    transaction.rollback();
                }
            } catch (Exception rollbackException) {
                log.error(rollbackException.getMessage(), rollbackException);
            }
            throw e;
        }
    }

    public TaskDTO update(TaskCommand taskCommand) {
        Transaction transaction = null;
        try (Session session = getSessionFactory().openSession()) {
            transaction = session.beginTransaction();

            Validator.validateTaskTitle(taskCommand.getTitle());
            Validator.validateTaskDescription(taskCommand.getDescription());
            Validator.validateTaskTag(taskCommand.getTags());

            TaskMapper mapper = (TaskMapper) getMapper();

            Task existingTask = getRepository().findById(session, taskCommand.getId()).orElse(null);
            mapper.partialUpdate(taskCommand, existingTask);
            getRepository().update(existingTask, session);
            transaction.commit();

            return mapper.mapToDTO(existingTask);
        } catch (Exception e) {
            log.error(e.getMessage(), e);
            try {
                if (transaction != null) {
                    transaction.rollback();
                }
            } catch (Exception rollbackException) {
                log.error(rollbackException.getMessage(), rollbackException);
            }
            throw e;
        }
    }


    public TaskDTO getTaskById(Long id) {
        try (Session session = getSessionFactory().openSession()) {
            return getMapper().mapToDTO(getRepository().findById(session, id).orElseThrow(
                    () -> new TaskNotFoundException("Task not found with id: " + id)));
        }
    }
}