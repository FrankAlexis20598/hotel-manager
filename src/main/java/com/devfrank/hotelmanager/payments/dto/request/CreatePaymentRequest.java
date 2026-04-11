package com.devfrank.hotelmanager.payments.dto.request;

import java.math.BigDecimal;
import java.time.LocalDate;

public record CreatePaymentRequest(
        BigDecimal amount,
        LocalDate paymentDate,
        String paymentMethod
) {
}