package com.devfrank.hotelmanager.access.dto.command;

import java.util.List;
import java.util.UUID;

public record SaveRoleCommand(
        String name,
        String description,
        List<UUID> permissions
) {
}