package com.devfrank.hotelmanager.payments.dto;

import com.devfrank.hotelmanager.payments.util.enums.PaymentMethod;
import com.devfrank.hotelmanager.payments.util.enums.PaymentStatus;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.UUID;

public record PaymentDTO(
        UUID id,
        BigDecimal amount,
        LocalDate paymentDate,
        PaymentMethod paymentMethod,
        PaymentStatus status
) {
}