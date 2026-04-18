package com.devfrank.hotelmanager.reservations.dto.request;

import java.time.LocalDateTime;

public record CreateReservationRequest(
        LocalDateTime checkInDate,
        LocalDateTime checkOutDate,
        String customerId,
        String roomId
) {
}