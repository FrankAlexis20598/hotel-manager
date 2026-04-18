package com.devfrank.hotelmanager.reservations.service.api;

import java.util.UUID;

public interface RoomStatusManager {
    void markAsOccupied(UUID roomId);

    void markAsCleaning(UUID roomId);

    void releaseRoom(UUID roomId);
}