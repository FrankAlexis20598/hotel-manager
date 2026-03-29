package com.devfrank.hotelmanager.rooms.dto.command;

import com.devfrank.hotelmanager.rooms.util.enums.RoomStatus;
import com.devfrank.hotelmanager.rooms.util.enums.RoomType;

import java.math.BigDecimal;

public record UpdateRoomCommand(
        String number,
        RoomType type,
        BigDecimal price,
        RoomStatus status
) {
}