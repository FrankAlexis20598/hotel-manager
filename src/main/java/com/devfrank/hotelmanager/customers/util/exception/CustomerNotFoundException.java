package com.devfrank.hotelmanager.customers.util.exception;

import com.devfrank.hotelmanager.shared.exception.ResourceNotFoundException;

import java.util.UUID;

public class CustomerNotFoundException extends ResourceNotFoundException {
    public CustomerNotFoundException(UUID id) {
        super("Customer with id " + id + " not found");
    }
}