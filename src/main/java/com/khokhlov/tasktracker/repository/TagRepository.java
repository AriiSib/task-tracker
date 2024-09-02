package com.khokhlov.tasktracker.repository;

import com.khokhlov.tasktracker.model.entity.Tag;
import org.hibernate.Session;

import java.util.List;
import java.util.Optional;

public class TagRepository implements Repository<Tag, Long> {
    @Override
    public Optional<Tag> findById(Long aLong) {
        return Optional.empty();
    }

    @Override
    public List<Tag> findAll() {
        return List.of();
    }

    public List<Tag> findAll(Session session) {
        return session.createQuery("from Tag")
                .list();
    }

    @Override
    public void save(Tag tag) {

    }

    @Override
    public void delete(Tag tag) {

    }

    @Override
    public void deleteById(Long aLong) {

    }

    @Override
    public Tag update(Tag tag) {
        return null;
    }
}
