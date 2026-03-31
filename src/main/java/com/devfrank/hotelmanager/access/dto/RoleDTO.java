package com.devfrank.hotelmanager.access.dto;

import java.util.List;
import java.util.UUID;

public record RoleDTO(
        UUID id,
        String name,
        String description,
        List<PermissionDTO> permissions
) {
}