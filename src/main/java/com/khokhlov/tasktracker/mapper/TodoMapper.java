package com.khokhlov.tasktracker.mapper;

import com.khokhlov.tasktracker.model.dto.TodoDTO;
import com.khokhlov.tasktracker.model.entity.Todo;
import org.mapstruct.*;
import org.mapstruct.factory.Mappers;

@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface TodoMapper {

    TodoMapper INSTANCE = Mappers.getMapper(TodoMapper.class);

    Todo toEntity(TodoDTO todoDto);

    TodoDTO toDto(Todo todo);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    Todo partialUpdate(TodoDTO todoDto, @MappingTarget Todo todo);
}