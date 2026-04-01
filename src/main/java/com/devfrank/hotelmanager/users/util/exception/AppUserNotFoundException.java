package com.devfrank.hotelmanager.users.util.exception;

import com.devfrank.hotelmanager.shared.exception.ResourceNotFoundException;

import java.util.UUID;

public class AppUserNotFoundException extends ResourceNotFoundException {
    public AppUserNotFoundException(UUID id) {
        super("User with id " + id + " not found");
    }
}