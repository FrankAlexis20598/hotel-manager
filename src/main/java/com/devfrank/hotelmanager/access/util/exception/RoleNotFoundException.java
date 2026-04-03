package com.devfrank.hotelmanager.access.util.exception;

import java.util.UUID;

public class RoleNotFoundException extends RuntimeException {
    public RoleNotFoundException(UUID id) {
        super("Could not find room with id " + id);
    }
}