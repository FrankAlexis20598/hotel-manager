package com.devfrank.hotelmanager.reservations.dto;

import com.devfrank.hotelmanager.reservations.util.enums.ReservationStatus;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.UUID;

public record ReservationDTO(
        UUID id,
        String reservationNumber,
        String customerName,
        String roomNumber,
        String roomType,
        LocalDate checkInDate,
        LocalDate checkOutDate,
        BigDecimal totalAmount,
        ReservationStatus status
) {
}