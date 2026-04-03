package com.devfrank.hotelmanager.shared.base.service;

public interface UpdateService<ID, R, D> {
    D update(ID id, R request);
}