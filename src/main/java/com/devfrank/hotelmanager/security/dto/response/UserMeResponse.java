package com.devfrank.hotelmanager.security.dto.response;

import lombok.Builder;
import java.util.Set;

@Builder
public record UserMeResponse(
        String email,
        String role,
        Set<String> permissions
) {
}