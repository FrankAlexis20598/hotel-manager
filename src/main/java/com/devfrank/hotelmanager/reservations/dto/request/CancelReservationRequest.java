package com.devfrank.hotelmanager.reservations.dto.request;

public record CancelReservationRequest(
        String cancellationReason
) {
}