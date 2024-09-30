package com.khokhlov.tasktracker.service;

import com.khokhlov.tasktracker.mapper.TagMapper;
import com.khokhlov.tasktracker.model.command.TagCommand;
import com.khokhlov.tasktracker.model.dto.TagDTO;
import com.khokhlov.tasktracker.model.entity.Tag;
import com.khokhlov.tasktracker.repository.TagRepository;
import org.hibernate.SessionFactory;


public class TagService extends AbstractService<Tag, TagCommand, TagDTO, TagRepository> {

    public TagService(SessionFactory sessionFactory, TagRepository tagRepository, TagMapper tagMapper) {
        super(tagMapper, tagRepository, sessionFactory);
    }

}
