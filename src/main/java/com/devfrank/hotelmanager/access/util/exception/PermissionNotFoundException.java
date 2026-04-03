package com.devfrank.hotelmanager.access.util.exception;

import java.util.UUID;

public class PermissionNotFoundException extends RuntimeException {
    public PermissionNotFoundException(UUID id) {
        super("Could not find room with id " + id);
    }
}