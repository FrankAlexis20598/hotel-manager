package com.devfrank.hotelmanager.rooms.repository;

import com.devfrank.hotelmanager.rooms.entity.Room;
import com.devfrank.hotelmanager.shared.base.SearchableRepository;

import java.util.Optional;
import java.util.UUID;

public interface RoomRepository extends SearchableRepository<Room, UUID> {
    Optional<Room> findByNumber(String number);
}