package com.devfrank.hotelmanager.payments.dto.request;

import com.devfrank.hotelmanager.shared.constans.ValidationConstants;
import com.devfrank.hotelmanager.shared.util.validation.NotBlankIfPresent;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

public record CompletePaymentRequest(
        @NotBlankIfPresent
        @Size(max = 500)
        String observations,

        @NotBlankIfPresent
        @Pattern(regexp = "^(EFECTIVO|TARJETA_DEBITO|TARJETA_CREDITO|TRANSFERENCIA|BILLETERA_DIGITAL)$", message = ValidationConstants.PAYMENT_METHOD_INVALID)
        String paymentMethod
) {
}