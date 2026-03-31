package com.devfrank.hotelmanager.shared.base;

public interface CrudMapper<E, D, C, U> extends BaseMapper<E, D> {
    E toEntity(C command);

    void toEntity(E entity, U command);
}