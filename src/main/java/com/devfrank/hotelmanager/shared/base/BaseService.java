package com.devfrank.hotelmanager.shared.base;

import java.util.List;

public interface BaseService<CC, CU, D, ID> {
    List<D> findAll();

    D findById(ID id);

    D create(CC command);

    D update(ID id, CU command);

    void delete(ID id);
}