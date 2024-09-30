package com.khokhlov.tasktracker.mapper;

import com.khokhlov.tasktracker.model.command.TagCommand;
import com.khokhlov.tasktracker.model.dto.TagDTO;
import com.khokhlov.tasktracker.model.entity.Tag;
import org.mapstruct.*;
import org.mapstruct.factory.Mappers;

@org.mapstruct.Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface TagMapper extends Mapper<Tag, TagDTO, TagCommand> {

    TagMapper INSTANCE = Mappers.getMapper(TagMapper.class);

    @Override
    Tag mapToEntity(TagCommand entity);

    @Override
    TagDTO mapToDTO(Tag entity);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    Tag partialUpdate(TagDTO tagDTO, @MappingTarget Tag tag);
}