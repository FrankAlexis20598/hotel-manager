package com.devfrank.hotelmanager.access.dto;

import java.util.UUID;

public record RoleSummaryDTO(
        UUID id,
        String name
) {
}