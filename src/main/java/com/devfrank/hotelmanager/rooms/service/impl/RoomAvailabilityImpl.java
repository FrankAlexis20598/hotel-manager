package com.devfrank.hotelmanager.rooms.service.impl;

import com.devfrank.hotelmanager.reservations.service.api.RoomAvailability;
import com.devfrank.hotelmanager.rooms.entity.Room;
import com.devfrank.hotelmanager.rooms.repository.RoomRepository;
import com.devfrank.hotelmanager.rooms.util.enums.RoomStatus;
import com.devfrank.hotelmanager.shared.exception.BusinessException;
import com.devfrank.hotelmanager.shared.exception.ResourceIDsNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.util.UUID;

@Component
@RequiredArgsConstructor
public class RoomAvailabilityImpl implements RoomAvailability {

    private final RoomRepository roomRepository;

    @Override
    public BigDecimal verifyAndGetPrice(UUID roomId) {
        Room room = findRoomById(roomId);

        if (!room.getIsActive()) {
            throw new BusinessException(String.format("La habitación %s no está activa", room.getNumber()));
        }

        if (room.getStatus() == RoomStatus.MANTENIMIENTO) {
            throw new BusinessException(String.format("La habitación %s se encuentra en mantenimiento", room.getNumber()));
        }

        return room.getPrice();
    }

    private Room findRoomById(UUID roomId) {
        return roomRepository.findById(roomId)
                .orElseThrow(() -> new ResourceIDsNotFoundException(String.format("La habitación con ID '%s' no existe", roomId)));
    }
}