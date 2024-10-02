package com.khokhlov.tasktracker.service;

import com.khokhlov.tasktracker.mapper.Mapper;
import com.khokhlov.tasktracker.model.command.Command;
import com.khokhlov.tasktracker.model.dto.DTO;
import com.khokhlov.tasktracker.model.entity.Entity;
import com.khokhlov.tasktracker.repository.Repository;
import lombok.Getter;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;

import java.util.List;

@Getter
public abstract class AbstractService<E extends Entity, C extends Command, D extends DTO, R extends Repository<E, Long>> {
    private final Mapper<E, D, C> mapper;
    private final R repository;
    private final SessionFactory sessionFactory;

    AbstractService(Mapper<E, D, C> mapper, R repository, SessionFactory sessionFactory) {
        this.mapper = mapper;
        this.repository = repository;
        this.sessionFactory = sessionFactory;
    }

    public D save(C c) {
        Transaction transaction = null;
        try (Session session = sessionFactory.openSession()) {
            transaction = session.beginTransaction();

            var entity = mapper.mapToEntity(c);
            repository.save(entity, session);
            transaction.commit();

            return mapper.mapToDTO(entity);
        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();
            }
            throw e;
        }
    }

    public void deleteById(Long id) {
        Transaction transaction = null;
        try (Session session = sessionFactory.openSession()) {
            transaction = session.beginTransaction();

            repository.deleteById(id, session);
            transaction.commit();

        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();
            }
            throw e;
        }
    }

    public List<D> findAll() {
        Transaction transaction = null;
        try (Session session = sessionFactory.openSession()) {
            transaction = session.beginTransaction();

            var list = repository.findAll(session);
            transaction.commit();

            return list.stream()
                    .map(mapper::mapToDTO)
                    .toList();
        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();
            }
            throw e;
        }
    }
}