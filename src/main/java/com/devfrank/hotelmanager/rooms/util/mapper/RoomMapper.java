package com.devfrank.hotelmanager.rooms.util.mapper;

import com.devfrank.hotelmanager.rooms.dto.RoomDTO;
import com.devfrank.hotelmanager.rooms.dto.command.CreateRoomCommand;
import com.devfrank.hotelmanager.rooms.dto.command.UpdateRoomCommand;
import com.devfrank.hotelmanager.rooms.entity.Room;
import com.devfrank.hotelmanager.rooms.util.enums.RoomStatus;
import com.devfrank.hotelmanager.shared.base.CrudMapper;
import org.springframework.stereotype.Component;

import java.util.UUID;

@Component
public class RoomMapper implements CrudMapper<Room, RoomDTO, CreateRoomCommand, UpdateRoomCommand> {

    @Override
    public RoomDTO toDTO(Room entity) {
        return new RoomDTO(
                entity.getId(),
                entity.getNumber(),
                entity.getType(),
                entity.getPrice(),
                entity.getStatus()
        );
    }

    @Override
    public Room toEntity(CreateRoomCommand command) {
        Room room = new Room();
        room.setId(UUID.randomUUID());
        room.setNumber(command.number());
        room.setType(command.type());
        room.setPrice(command.price());
        room.setStatus(RoomStatus.DISPONIBLE);
        room.setIsActive(Boolean.TRUE);
        return room;
    }

    @Override
    public void toEntity(Room entity, UpdateRoomCommand command) {
        entity.setNumber(command.number());
        entity.setType(command.type());
        entity.setPrice(command.price());
        entity.setStatus(command.status());
    }

    public void toEntity(Room entity, CreateRoomCommand command) {
        entity.setNumber(command.number());
        entity.setType(command.type());
        entity.setPrice(command.price());
    }
}