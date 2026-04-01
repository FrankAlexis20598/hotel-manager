package com.devfrank.hotelmanager.users.dto;

import com.devfrank.hotelmanager.access.dto.RoleSummaryDTO;

import java.util.UUID;

public record AppUserDTO(
        UUID id,
        String email,
        String password,
        Boolean isActive,
        RoleSummaryDTO role
) {
}