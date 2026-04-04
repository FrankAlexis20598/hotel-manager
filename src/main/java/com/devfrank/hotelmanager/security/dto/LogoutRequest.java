package com.devfrank.hotelmanager.security.dto;

import jakarta.validation.constraints.NotBlank;

public record LogoutRequest(
        @NotBlank(message = "El refresh token es obligatorio")
        String refreshToken
) {
}