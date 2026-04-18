package com.devfrank.hotelmanager.reservations.dto.response;

import com.devfrank.hotelmanager.reservations.dto.ReservationDTO;

import java.math.BigDecimal;

public record ReservationResponse(
        String id,
        String reservationNumber,
        String customerName,
        String roomNumber,
        String roomType,
        String checkInDate,
        String checkOutDate,
        BigDecimal totalAmount,
        String status
) {
    public static ReservationResponse fromDTO(ReservationDTO dto) {
        return new ReservationResponse(
                dto.id().toString(),
                dto.reservationNumber(),
                dto.customerName(),
                dto.roomNumber(),
                dto.roomType(),
                dto.checkInDate().toString(),
                dto.checkOutDate().toString(),
                dto.totalAmount(),
                dto.status().name()
        );
    }
}