package com.devfrank.hotelmanager.security.dto.response;

import lombok.Builder;

import java.util.List;

@Builder
public record TokenResponse(
        String accessToken,
        String refreshToken,
        String email,
        List<String> authorities
) {
}