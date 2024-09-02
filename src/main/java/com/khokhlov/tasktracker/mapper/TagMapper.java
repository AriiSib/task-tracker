package com.khokhlov.tasktracker.mapper;

import com.khokhlov.tasktracker.model.dto.TagDTO;
import com.khokhlov.tasktracker.model.entity.Tag;
import org.mapstruct.*;
import org.mapstruct.factory.Mappers;

@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface TagMapper {

    TagMapper INSTANCE = Mappers.getMapper(TagMapper.class);

    Tag toEntity(TagDTO tagDTO);

    TagDTO toDTO(Tag tag);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    Tag partialUpdate(TagDTO tagDTO, @MappingTarget Tag tag);
}