package com.devfrank.hotelmanager.rooms.repository;

import com.devfrank.hotelmanager.rooms.entity.Room;
import com.devfrank.hotelmanager.shared.base.BaseRepository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface RoomRepository extends BaseRepository<Room, UUID> {
    List<Room> findAllByIsActiveTrue();

    Optional<Room> findByIdAndIsActiveTrue(UUID id);
}