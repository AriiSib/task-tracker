package com.khokhlov.tasktracker.mapper;

public interface Mapper<E, D, C> {
    E mapToEntity(C entity);

    D mapToDTO(E entity);
}