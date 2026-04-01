package com.devfrank.hotelmanager.users.dto.request;

import com.devfrank.hotelmanager.users.dto.command.CreateAppUserCommand;

import java.util.UUID;

public record CreateAppUserRequest(
        String email,
        String password,
        String roleId
) {
    public CreateAppUserCommand toCommand() {
        return new CreateAppUserCommand(
                email,
                password,
                UUID.fromString(roleId)
        );
    }
}