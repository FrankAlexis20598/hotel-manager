package com.devfrank.hotelmanager.rooms.dto.request;

import com.devfrank.hotelmanager.shared.constans.ValidationConstants;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;

public record UpdateRoomStatusRequest(
        @NotBlank(message = ValidationConstants.ROOM_STATUS_NOT_BLANK)
        @Pattern(regexp = "^(?i)(DISPONIBLE|RESERVADA|OCUPADA|LIMPIEZA|MANTENIMIENTO)$", message = ValidationConstants.ROOM_STATUS_INVALID)
        String status
) {
}