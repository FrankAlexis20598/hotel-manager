package com.devfrank.hotelmanager.users.dto.command;

import java.util.UUID;

public record UpdateAppUserCommand(
        String email,
        String password,
        Boolean isActive,
        UUID roleId
) {
}