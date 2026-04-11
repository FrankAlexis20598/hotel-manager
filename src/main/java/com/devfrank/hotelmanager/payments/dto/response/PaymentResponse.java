package com.devfrank.hotelmanager.payments.dto.response;

import com.devfrank.hotelmanager.payments.dto.PaymentDTO;

import java.math.BigDecimal;

public record PaymentResponse(
        String id,
        BigDecimal amount,
        String paymentDate,
        String paymentMethod,
        String status
) {
    public static PaymentResponse fromDTO(PaymentDTO dto) {
        return new PaymentResponse(
                dto.id().toString(),
                dto.amount(),
                dto.paymentDate().toString(),
                dto.paymentMethod().name(),
                dto.status().name()
        );
    }
}