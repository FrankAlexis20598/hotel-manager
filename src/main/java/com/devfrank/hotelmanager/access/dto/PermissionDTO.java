package com.devfrank.hotelmanager.access.dto;

import java.util.UUID;

public record PermissionDTO(
        UUID id,
        String module,
        String action
) {
}