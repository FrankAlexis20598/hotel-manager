package com.devfrank.hotelmanager.customers.dto.request;

import com.devfrank.hotelmanager.customers.dto.command.SaveCustomerCommand;
import com.devfrank.hotelmanager.customers.util.enums.DocumentType;

public record SaveCustomerRequest(
        String names,
        String surnames,
        String email,
        String phone,
        String documentType,
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