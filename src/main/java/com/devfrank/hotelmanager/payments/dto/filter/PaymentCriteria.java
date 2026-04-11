package com.devfrank.hotelmanager.payments.dto.filter;

import com.devfrank.hotelmanager.shared.constans.ValidationConstants;
import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class PaymentCriteria {

    @Pattern(regexp = "PENDIENTE|PAGADO|ANULADO|REEMBOLSADO", message = ValidationConstants.PAYMENT_STATUS_INVALID)
    private String status;

    @Pattern(regexp = "^\\d{4}-(0[1-9]|1[0-2])-(0[1-9]|[12][0-9]|3[01])$", message = ValidationConstants.PAYMENT_DATE_FORMAT_INVALID)
    private String dateFrom;

    @Pattern(regexp = "^\\d{4}-(0[1-9]|1[0-2])-(0[1-9]|[12][0-9]|3[01])$", message = ValidationConstants.PAYMENT_DATE_FORMAT_INVALID)
    private String dateTo;

    @Pattern(regexp = "EFECTIVO|TARJETA_DEBITO|TARJETA_CREDITO|TRANSFERENCIA|BILLETERA_DIGITAL", message = ValidationConstants.PAYMENT_METHOD_INVALID)
    private String paymentMethod;
}
