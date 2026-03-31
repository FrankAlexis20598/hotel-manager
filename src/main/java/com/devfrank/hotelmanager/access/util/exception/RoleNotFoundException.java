package com.devfrank.hotelmanager.access.util.exception;

import com.devfrank.hotelmanager.shared.exception.ResourceNotFoundException;

import java.util.UUID;

public class RoleNotFoundException extends ResourceNotFoundException {
    public RoleNotFoundException(UUID id) {
        super("Role with id " + id + " not found");
    }
}