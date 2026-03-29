package com.devfrank.hotelmanager.rooms.util.enums;

public enum RoomType {
    INDIVIDUAL, DOBLE, SUITE;

    public static RoomType fromType(String roomType) {
        for (RoomType type : RoomType.values()) {
            if (type.name().equalsIgnoreCase(roomType)) {
                return type;
            }
        }
        return null;
    }
}