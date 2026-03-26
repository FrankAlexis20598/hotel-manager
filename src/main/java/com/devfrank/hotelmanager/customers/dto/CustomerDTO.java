package com.devfrank.hotelmanager.customers.dto;

import com.devfrank.hotelmanager.customers.util.enums.DocumentType;

import java.util.UUID;

public record CustomerDTO(
        UUID id,
        String names,
        String surnames,
        String email,
        String phone,
        DocumentType documentType,
        String documentNumber,
        Boolean isActive
) {
}