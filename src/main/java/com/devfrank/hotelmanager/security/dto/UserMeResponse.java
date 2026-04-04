package com.devfrank.hotelmanager.security.dto;

import lombok.Builder;
import java.util.Set;

@Builder
public record UserMeResponse(
        String email,
        String role,
        Set<String> permissions
) {
}