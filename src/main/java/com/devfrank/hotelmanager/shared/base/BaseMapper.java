package com.devfrank.hotelmanager.shared.base;

import java.util.List;
import java.util.stream.Collectors;

public interface BaseMapper<E, D> {
    D toDTO(E entity);

    default List<D> toDTOs(List<E> entities) {
        return entities.stream().map(this::toDTO).collect(Collectors.toList());
    }
}