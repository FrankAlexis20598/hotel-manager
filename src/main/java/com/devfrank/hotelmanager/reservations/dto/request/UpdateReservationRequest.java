package com.devfrank.hotelmanager.reservations.dto.request;

import java.time.LocalDateTime;

public record UpdateReservationRequest(
        LocalDateTime checkInDate,
        LocalDateTime checkOutDate,
        String roomId
) {
}