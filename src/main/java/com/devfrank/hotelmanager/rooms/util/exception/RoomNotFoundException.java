package com.devfrank.hotelmanager.rooms.util.exception;

import com.devfrank.hotelmanager.shared.exception.ResourceNotFoundException;

import java.util.UUID;

public class RoomNotFoundException extends ResourceNotFoundException {
    public RoomNotFoundException(UUID id) {
        super("Room with id " + id + " not found");
    }
}