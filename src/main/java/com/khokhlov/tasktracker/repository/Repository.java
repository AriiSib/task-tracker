package com.khokhlov.tasktracker.repository;

import org.hibernate.Session;

import java.io.Serializable;
import java.lang.reflect.ParameterizedType;
import java.util.List;
import java.util.Optional;

public interface Repository<E, ID extends Serializable> {

    default Optional<E> findById(Session session, ID id) {
        return Optional.ofNullable(session.get(getEntityClass(), id));
    }

    @SuppressWarnings("unchecked")
    default Class<E> getEntityClass() {
        return (Class<E>) ((ParameterizedType) getClass().getGenericInterfaces()[0])
                .getActualTypeArguments()[0];
    }

    default List<E> findAll(Session session) {
        Class<E> entityType = getEntityClass();
        return session.createQuery("from " + entityType.getSimpleName(), entityType).list();
    }

    default void save(E e, Session session) {
        session.persist(e);
    }

    default void deleteById(ID id, Session session) {
        session.remove(findById(session, id).orElseThrow());
    }

    default E update(E e, Session session) {
        return session.merge(e);
    }
}