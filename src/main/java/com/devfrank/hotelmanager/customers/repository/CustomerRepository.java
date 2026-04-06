package com.devfrank.hotelmanager.customers.repository;

import com.devfrank.hotelmanager.customers.entity.Customer;
import com.devfrank.hotelmanager.shared.base.repository.SearchableRepository;

import java.util.Optional;
import java.util.UUID;

public interface CustomerRepository extends SearchableRepository<Customer, UUID> {
    Optional<Customer> findByDocumentNumber(String documentNumber);
}