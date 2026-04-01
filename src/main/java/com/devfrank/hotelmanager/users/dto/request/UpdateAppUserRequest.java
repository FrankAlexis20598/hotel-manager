package com.devfrank.hotelmanager.users.dto.request;

import com.devfrank.hotelmanager.users.dto.command.UpdateAppUserCommand;

import java.util.UUID;

public record UpdateAppUserRequest(
        String email,
        String password,
        boolean isActive,
        String roleId
) {
    public UpdateAppUserCommand toCommand() {
        return new UpdateAppUserCommand(
                email,
                password,
                isActive,
                UUID.fromString(roleId)
        );
    }
}