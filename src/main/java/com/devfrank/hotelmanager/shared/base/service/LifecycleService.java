package com.devfrank.hotelmanager.shared.base.service;

public interface LifecycleService<ID> {
    void deactivate(ID id);

    void reactivate(ID id);
}