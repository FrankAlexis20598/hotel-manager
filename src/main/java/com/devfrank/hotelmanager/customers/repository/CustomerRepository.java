package com.devfrank.hotelmanager.customers.repository;

import com.devfrank.hotelmanager.customers.entity.Customer;
import com.devfrank.hotelmanager.shared.base.BaseRepository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface CustomerRepository extends BaseRepository<Customer, UUID> {
    List<Customer> findAllByIsActiveTrue();

    Optional<Customer> findByIdAndIsActiveTrue(UUID id);
}