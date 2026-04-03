package com.devfrank.hotelmanager.users.util.exception;

import java.util.UUID;

public class AppUserNotFoundException extends RuntimeException {
    public AppUserNotFoundException(UUID id) {
        super("Could not find user with id " + id);
    }
}