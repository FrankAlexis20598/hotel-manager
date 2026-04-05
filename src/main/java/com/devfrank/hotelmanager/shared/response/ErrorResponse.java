package com.devfrank.hotelmanager.shared.response;

import com.fasterxml.jackson.annotation.JsonInclude;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

@JsonInclude(JsonInclude.Include.NON_NULL)
public record ErrorResponse(
        int status,
        String message,
        LocalDateTime timestamp,
        List<String> details,
        Map<String, Object> metadata
) {
    public ErrorResponse(int status, String message, LocalDateTime timestamp) {
        this(status, message, timestamp, null, null);
    }

    public ErrorResponse(int status, String message, LocalDateTime timestamp, List<String> details) {
        this(status, message, timestamp, details, null);
    }
}