package com.devfrank.hotelmanager.users.dto;

import com.devfrank.hotelmanager.access.dto.RoleDTO;

import java.util.UUID;

public record AppUserDTO(
        UUID id,
        String email,
        Boolean isActive,
        RoleDTO role
) {
}