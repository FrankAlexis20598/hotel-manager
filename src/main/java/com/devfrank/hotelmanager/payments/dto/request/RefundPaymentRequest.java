package com.devfrank.hotelmanager.payments.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record RefundPaymentRequest(
        @NotBlank(message = "El motivo del reembolso es obligatorio")
        @Size(min = 10, max = 500)
        String refundReason
) {
}