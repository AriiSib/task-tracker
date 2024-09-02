package com.khokhlov.tasktracker.service;

import com.khokhlov.tasktracker.mapper.TagMapper;
import com.khokhlov.tasktracker.model.dto.TagDTO;
import com.khokhlov.tasktracker.repository.TagRepository;
import lombok.RequiredArgsConstructor;
import org.hibernate.Session;
import org.hibernate.SessionFactory;

import java.util.List;

@RequiredArgsConstructor
public class TagService {
    private final SessionFactory sessionFactory;
    private final TagRepository tagRepository;
    private final TagMapper tagMapper;

    public List<TagDTO> getAllTags() {
        try (Session session = sessionFactory.openSession()) {
            return tagRepository.findAll(session)
                    .stream()
                    .map(tagMapper::toDTO)
                    .toList();
        }
    }
}
