package com.devfrank.hotelmanager.rooms.util.enums;

public enum RoomStatus {
    DISPONIBLE, OCUPADA, MANTENIMIENTO;

    public static RoomStatus fromStatus(String roomStatus) {
        for (RoomStatus status : RoomStatus.values()) {
            if (status.name().equalsIgnoreCase(roomStatus)) {
                return status;
            }
        }
        return null;
    }
}