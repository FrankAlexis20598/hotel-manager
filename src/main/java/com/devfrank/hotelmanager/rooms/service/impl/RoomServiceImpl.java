package com.devfrank.hotelmanager.rooms.service.impl;

import com.devfrank.hotelmanager.rooms.dto.RoomDTO;
import com.devfrank.hotelmanager.rooms.dto.command.CreateRoomCommand;
import com.devfrank.hotelmanager.rooms.dto.command.UpdateRoomCommand;
import com.devfrank.hotelmanager.rooms.entity.Room;
import com.devfrank.hotelmanager.rooms.repository.RoomRepository;
import com.devfrank.hotelmanager.rooms.service.RoomService;
import com.devfrank.hotelmanager.rooms.util.exception.RoomNotFoundException;
import com.devfrank.hotelmanager.rooms.util.mapper.RoomMapper;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class RoomServiceImpl implements RoomService {

    private final RoomRepository roomRepository;
    private final RoomMapper roomMapper;

    @Override
    public List<RoomDTO> findAll() {
        return roomRepository.findAllByIsActiveTrue().stream()
                .map(roomMapper::toDTO)
                .toList();
    }

    @Override
    public RoomDTO findById(UUID id) {
        return roomRepository.findByIdAndIsActiveTrue(id)
                .map(roomMapper::toDTO)
                .orElseThrow(() -> new RoomNotFoundException(id));
    }

    @Override
    public RoomDTO create(CreateRoomCommand command) {
        Room room = roomMapper.toEntity(command);
        return roomMapper.toDTO(roomRepository.save(room));
    }

    @Override
    public RoomDTO update(UUID id, UpdateRoomCommand command) {
        Room room = roomRepository.findByIdAndIsActiveTrue(id)
                .orElseThrow(() -> new RoomNotFoundException(id));
        roomMapper.toEntity(room, command);
        return roomMapper.toDTO(roomRepository.save(room));
    }

    @Transactional
    @Override
    public void delete(UUID id) {
        Room room = roomRepository.findByIdAndIsActiveTrue(id)
                .orElseThrow(() -> new RoomNotFoundException(id));
        room.setIsActive(false);
        roomRepository.save(room);
    }
}