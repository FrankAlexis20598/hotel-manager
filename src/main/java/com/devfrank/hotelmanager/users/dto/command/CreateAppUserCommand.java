package com.devfrank.hotelmanager.users.dto.command;

import java.util.UUID;

public record CreateAppUserCommand(
        String email,
        String password,
        UUID roleId
) {
}