package com.yasirakbal.ordermanagementsystem.common.dto;

public interface EntityDtoMapper<E, D> {
    D entityToDto(E entity);
    E dtoToEntity(D dto);
}
