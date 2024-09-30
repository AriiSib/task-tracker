package com.khokhlov.tasktracker.mapper;

import com.khokhlov.tasktracker.model.command.TaskCommand;
import com.khokhlov.tasktracker.model.dto.TaskDTO;
import com.khokhlov.tasktracker.model.entity.Task;
import org.mapstruct.*;
import org.mapstruct.factory.Mappers;

@org.mapstruct.Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface TaskMapper extends Mapper<Task, TaskDTO, TaskCommand> {

    TaskMapper INSTANCE = Mappers.getMapper(TaskMapper.class);

    @Override
    Task mapToEntity(TaskCommand entity);

    @Override
    TaskDTO mapToDTO(Task entity);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    Task partialUpdate(TaskCommand taskCommand, @MappingTarget Task task);
}