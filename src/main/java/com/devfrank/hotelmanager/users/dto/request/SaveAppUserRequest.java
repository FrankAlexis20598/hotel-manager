package com.devfrank.hotelmanager.users.dto.request;

public record SaveAppUserRequest(
        String email,
        String password,
        String roleId
) {
}