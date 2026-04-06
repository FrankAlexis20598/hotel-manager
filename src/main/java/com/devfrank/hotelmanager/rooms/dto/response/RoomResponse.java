package com.devfrank.hotelmanager.rooms.dto.response;

import com.devfrank.hotelmanager.rooms.dto.RoomDTO;
import com.fasterxml.jackson.annotation.JsonInclude;

import java.math.BigDecimal;

@JsonInclude(JsonInclude.Include.NON_NULL)
public record RoomResponse(
        String id,
        String number,
        String type,
        BigDecimal price,
        String status
) {
    public static RoomResponse fromDTO(RoomDTO dto) {
        return new RoomResponse(
                dto.id().toString(),
                dto.number(),
                dto.type().name(),
                dto.price(),
                dto.status().name()
        );
    }
}