package com.devfrank.hotelmanager.rooms.service.impl;

import com.devfrank.hotelmanager.reservations.service.api.RoomStatusManager;
import com.devfrank.hotelmanager.rooms.entity.Room;
import com.devfrank.hotelmanager.rooms.repository.RoomRepository;
import com.devfrank.hotelmanager.rooms.util.enums.RoomStatus;
import com.devfrank.hotelmanager.shared.constans.ResourceConstants;
import com.devfrank.hotelmanager.shared.exception.ResourceNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.UUID;

@Component
@RequiredArgsConstructor
public class RoomStatusManagerImpl implements RoomStatusManager {

    private final RoomRepository roomRepository;

    @Override
    public void markAsOccupied(UUID roomId) {
        updateRoomStatus(roomId, RoomStatus.OCUPADA);
    }

    @Override
    public void markAsCleaning(UUID roomId) {
        updateRoomStatus(roomId, RoomStatus.LIMPIEZA);
    }

    @Override
    public void releaseRoom(UUID roomId) {
        updateRoomStatus(roomId, RoomStatus.DISPONIBLE);
    }

    private void updateRoomStatus(UUID roomId, RoomStatus roomStatus) {
        Room room = findRoomById(roomId);
        room.setStatus(RoomStatus.DISPONIBLE);
        roomRepository.save(room);
    }

    private Room findRoomById(UUID roomId) {
        return roomRepository.findById(roomId)
                .orElseThrow(() -> new ResourceNotFoundException(ResourceConstants.ROOM, roomId.toString()));
    }
}