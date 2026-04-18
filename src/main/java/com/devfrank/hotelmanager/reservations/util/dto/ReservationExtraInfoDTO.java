package com.devfrank.hotelmanager.reservations.util.dto;

import java.math.BigDecimal;

public record ReservationExtraInfoDTO(
        String reservationNumber,
        BigDecimal basePrice
) {
}