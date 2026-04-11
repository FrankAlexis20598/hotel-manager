package com.devfrank.hotelmanager.payments.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record CancelPaymentRequest(
        @NotBlank(message = "El motivo de la anulación es obligatorio")
        @Size(min = 5, max = 255)
        String reason
) {
}