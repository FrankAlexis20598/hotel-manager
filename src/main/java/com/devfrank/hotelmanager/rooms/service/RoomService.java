package com.devfrank.hotelmanager.rooms.service;

import com.devfrank.hotelmanager.rooms.dto.RoomDTO;
import com.devfrank.hotelmanager.rooms.dto.command.CreateRoomCommand;
import com.devfrank.hotelmanager.rooms.dto.command.UpdateRoomCommand;
import com.devfrank.hotelmanager.shared.base.BaseService;

import java.util.UUID;

public interface RoomService extends BaseService<CreateRoomCommand, UpdateRoomCommand, RoomDTO, UUID> {
}