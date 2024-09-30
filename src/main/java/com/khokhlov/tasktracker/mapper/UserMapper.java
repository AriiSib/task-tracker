package com.khokhlov.tasktracker.mapper;

import com.khokhlov.tasktracker.model.command.UserCommand;
import com.khokhlov.tasktracker.model.dto.UserDTO;
import com.khokhlov.tasktracker.model.entity.User;
import org.mapstruct.*;
import org.mapstruct.factory.Mappers;

@org.mapstruct.Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface UserMapper extends Mapper<User, UserDTO, UserCommand> {

    UserMapper INSTANCE = Mappers.getMapper(UserMapper.class);

    @Override
    User mapToEntity(UserCommand entity);

    @Override
    UserDTO mapToDTO(User entity);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    User partialUpdate(UserDTO userDTO, @MappingTarget User user);
}