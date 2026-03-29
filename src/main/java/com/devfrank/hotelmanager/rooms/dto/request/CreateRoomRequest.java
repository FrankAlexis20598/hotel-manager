package com.devfrank.hotelmanager.rooms.dto.request;

import com.devfrank.hotelmanager.rooms.dto.command.CreateRoomCommand;
import com.devfrank.hotelmanager.rooms.util.enums.RoomType;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

import java.math.BigDecimal;

public record CreateRoomRequest(
        @NotBlank @Size(min = 3, max = 3) @Pattern(regexp = "^\\d{3}$", message = "{room.number.pattern}") String number,
        @NotBlank @Pattern(regexp = "^(?i)(INDIVIDUAL|DOBLE|SUITE)$", message = "{room.type.invalid}") String type,
        @NotNull @DecimalMin(value = "0.0", inclusive = false, message = "{room.price.min}") BigDecimal price
) {
    public CreateRoomCommand toCommand() {
        return new CreateRoomCommand(
                number,
                RoomType.fromType(type),
                price
        );
    }
}