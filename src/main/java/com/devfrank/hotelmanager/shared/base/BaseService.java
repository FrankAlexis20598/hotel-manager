package com.devfrank.hotelmanager.shared.base;

import java.util.List;

public interface BaseService<C, U, D, ID> {
    List<D> findAll();

    D findById(ID id);

    D create(C command);

    D update(ID id, U command);

    void delete(ID id);
}