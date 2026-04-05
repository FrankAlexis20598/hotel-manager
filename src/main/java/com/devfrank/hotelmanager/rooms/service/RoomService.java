package com.devfrank.hotelmanager.rooms.service;

import com.devfrank.hotelmanager.rooms.dto.RoomDTO;
import com.devfrank.hotelmanager.rooms.dto.filter.RoomCriteria;
import com.devfrank.hotelmanager.rooms.dto.request.SaveRoomRequest;
import com.devfrank.hotelmanager.rooms.dto.request.UpdateRoomStatusRequest;
import com.devfrank.hotelmanager.shared.base.service.CreateService;
import com.devfrank.hotelmanager.shared.base.service.LifecycleService;
import com.devfrank.hotelmanager.shared.base.service.ReadService;
import com.devfrank.hotelmanager.shared.base.service.UpdateService;

import java.util.UUID;

public interface RoomService extends
        ReadService<RoomDTO, UUID, RoomCriteria>,
        CreateService<SaveRoomRequest, RoomDTO>,
        UpdateService<UUID, SaveRoomRequest, RoomDTO>,
        LifecycleService<UUID> {
    void updateStatus(UUID id, UpdateRoomStatusRequest request);
}