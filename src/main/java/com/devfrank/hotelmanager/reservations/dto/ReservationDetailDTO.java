package com.devfrank.hotelmanager.reservations.dto;

import com.devfrank.hotelmanager.customers.dto.CustomerDTO;
import com.devfrank.hotelmanager.payments.dto.PaymentDTO;
import com.devfrank.hotelmanager.reservations.util.enums.ReservationStatus;
import com.devfrank.hotelmanager.rooms.dto.RoomDTO;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

public record ReservationDetailDTO(
        UUID id,
        String reservationNumber,
        CustomerDTO customer,
        RoomDTO room,
        LocalDate checkInDate,
        LocalDate checkOutDate,
        ReservationStatus status,
        BigDecimal totalAmount,
        List<PaymentDTO> payments,
        BigDecimal remainingBalance,
        String createdBy,
        LocalDateTime createdAt
) {
}