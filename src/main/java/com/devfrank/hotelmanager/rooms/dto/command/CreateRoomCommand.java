package com.devfrank.hotelmanager.rooms.dto.command;

import com.devfrank.hotelmanager.rooms.util.enums.RoomType;

import java.math.BigDecimal;

public record CreateRoomCommand(
        String number,
        RoomType type,
        BigDecimal price
) {
}