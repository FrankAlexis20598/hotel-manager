package com.devfrank.hotelmanager.customers.dto.command;

import com.devfrank.hotelmanager.customers.util.enums.DocumentType;

public record SaveCustomerCommand(
        String names,
        String surnames,
        String email,
        String phone,
        DocumentType documentType,
        String documentNumber
) {
}