package com.devfrank.hotelmanager.customers.dto.filter;

public record CustomerCriteria(
        String fullName,
        String email,
        String documentNumber
) {
}