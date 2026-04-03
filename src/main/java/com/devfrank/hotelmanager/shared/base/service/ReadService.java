package com.devfrank.hotelmanager.shared.base.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface ReadService<D, ID, F> {
    Page<D> findAllBy(F filter, Pageable pageable);

    D findById(ID id);
}