package com.devfrank.hotelmanager.rooms.util.exception;

import java.util.UUID;

public class RoomNotFoundException extends RuntimeException {
    public RoomNotFoundException(UUID id) {
        super("Could not find room with id " + id);
    }
}