package com.devfrank.hotelmanager.shared.base;

import java.util.List;

public interface BaseMapper<E, D, CC, CU> {

    D toDTO(E entity);

    E toEntity(CC command);

    E toEntity(E entity, CU command);

    default List<D> toDTOs(List<E> entities) {
        return entities.stream().map(this::toDTO).toList();
    }
}