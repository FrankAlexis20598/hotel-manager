package com.devfrank.hotelmanager.customers.dto.request;

import com.devfrank.hotelmanager.customers.dto.command.SaveCustomerCommand;
import com.devfrank.hotelmanager.customers.util.enums.DocumentType;
import com.devfrank.hotelmanager.customers.util.validation.ValidDocument;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

@ValidDocument
public record SaveCustomerRequest(
        @NotBlank(message = "{customer.names.notblank}")
        @Size(min = 2, max = 100, message = "{customer.names.size}")
        String names,
        @NotBlank(message = "{customer.surnames.notblank}")
        @Size(min = 2, max = 100, message = "{customer.surnames.size}")
        String surnames,
        @NotBlank(message = "{customer.email.notblank}")
        @Email(message = "{customer.email.format}")
        @Size(min = 6, max = 150, message = "{customer.email.size}")
        String email,
        @Size(min = 9, max = 20, message = "{customer.phone.size}")
        @Pattern(regexp = "^(\\+51)?9\\d{8}$", message = "{customer.phone.pattern}")
        String phone,
        @NotBlank(message = "{customer.documentType.notblank}")
        @Size(min = 2, max = 2, message = "{customer.documentType.size}")
        @Pattern(regexp = "01|04", message = "{customer.documentType.pattern}")
        String documentType,
        @NotBlank(message = "{customer.documentNumber.notblank}")
        @Size(min = 8, max = 12, message = "{customer.documentNumber.size}")
        @Pattern(regexp = "^(\\d{8}|[a-zA-Z0-9]{8,12})$", message = "{customer.documentNumber.pattern}")
        String documentNumber
) {
    public SaveCustomerCommand toCommand() {
        return new SaveCustomerCommand(
                names,
                surnames,
                email,
                phone,
                DocumentType.fromValue(documentType),
                documentNumber
        );
    }
}