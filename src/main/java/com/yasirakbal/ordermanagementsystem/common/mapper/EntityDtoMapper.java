package com.yasirakbal.ordermanagementsystem.common.mapper;

public interface EntityDtoMapper<E, D> {
    D entityToDto(E entity);
    E dtoToEntity(D dto);
}
