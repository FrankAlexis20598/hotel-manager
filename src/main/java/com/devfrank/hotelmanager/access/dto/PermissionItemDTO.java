package com.devfrank.hotelmanager.access.dto;

import java.util.UUID;

public record PermissionItemDTO(
        UUID id,
        String action
) {
}