package com.devfrank.hotelmanager.rooms.service.api;

import java.util.UUID;

public interface RoomReservationValidator {
    boolean hasActiveOrFutureReservations(UUID roomId);
}