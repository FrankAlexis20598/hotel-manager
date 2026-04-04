package com.devfrank.hotelmanager.rooms.dto.request;

import com.devfrank.hotelmanager.rooms.dto.command.UpdateRoomCommand;
import com.devfrank.hotelmanager.rooms.util.enums.RoomStatus;
import com.devfrank.hotelmanager.rooms.util.enums.RoomType;
import com.devfrank.hotelmanager.shared.constans.ValidationConstants;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

import java.math.BigDecimal;

public record UpdateRoomRequest(
        @NotBlank @Size(min = 3, max = 3) @Pattern(regexp = "^\\d{3}$", message = ValidationConstants.ROOM_NUMBER_PATTERN) String number,
        @NotBlank @Pattern(regexp = "^(?i)(INDIVIDUAL|DOBLE|SUITE)$", message = ValidationConstants.ROOM_TYPE_INVALID) String type,
        @NotNull @DecimalMin(value = "0.0", inclusive = false, message = ValidationConstants.ROOM_PRICE_MIN) BigDecimal price,
        @NotBlank @Pattern(regexp = "^(?i)(DISPONIBLE|OCUPADA|MANTENIMIENTO)$", message = ValidationConstants.ROOM_STATUS_INVALID) String status
) {
    public UpdateRoomCommand toCommand() {
        return new UpdateRoomCommand(
                number,
                RoomType.fromType(type),
                price,
                RoomStatus.fromStatus(status)
        );
    }
}
