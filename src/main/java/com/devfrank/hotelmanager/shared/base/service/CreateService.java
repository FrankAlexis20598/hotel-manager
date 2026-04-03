package com.devfrank.hotelmanager.shared.base.service;

public interface CreateService<R, D> {
    D create(R request);
}