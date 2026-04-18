package com.devfrank.hotelmanager.customers.dto.response;

import com.devfrank.hotelmanager.customers.dto.CustomerDTO;
import com.fasterxml.jackson.annotation.JsonInclude;

@JsonInclude(JsonInclude.Include.NON_NULL)
public record CustomerResponse(
        String id,
        String names,
        String surnames,
        String email,
        String phone,
        String documentType,
        String documentNumber,
        Boolean isActive
) {
    public static CustomerResponse fromDTO(CustomerDTO dto) {
        return new CustomerResponse(
                dto.id().toString(),
                dto.names(),
                dto.surnames(),
                dto.email(),
                dto.phone(),
                dto.documentType().getValue(),
                dto.documentNumber(),
                dto.isActive()
        );
    }
}