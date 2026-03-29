package com.devfrank.hotelmanager.rooms.dto;

import com.devfrank.hotelmanager.rooms.util.enums.RoomStatus;
import com.devfrank.hotelmanager.rooms.util.enums.RoomType;

import java.math.BigDecimal;
import java.util.UUID;

public record RoomDTO(
        UUID id,
        String number,
        RoomType type,
        BigDecimal price,
        RoomStatus status
) {
}