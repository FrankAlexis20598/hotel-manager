package com.devfrank.hotelmanager.payments.dto.response;

import com.devfrank.hotelmanager.payments.dto.PaymentDTO;
import com.fasterxml.jackson.annotation.JsonInclude;

import java.math.BigDecimal;

@JsonInclude(JsonInclude.Include.NON_NULL)
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