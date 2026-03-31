package com.devfrank.hotelmanager.access.util.exception;

import com.devfrank.hotelmanager.shared.exception.ResourceNotFoundException;

import java.util.UUID;

public class PermissionNotFoundException extends ResourceNotFoundException {
    public PermissionNotFoundException(UUID id) {
        super("Permission with id " + id + " not found");
    }
}