package com.devfrank.hotelmanager.shared.response;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

public record ErrorResponse(
        int status,
        String message,
        LocalDateTime timestamp,
        List<String> details,
        Map<String, Object> metadata
) {
    public ErrorResponse(int status, String message, LocalDateTime timestamp) {
        this(status, message, timestamp, List.of(), Map.of());
    }

    public ErrorResponse(int status, String message, LocalDateTime timestamp, List<String> details) {
        this(status, message, timestamp, details, Map.of());
    }
}