package com.devfrank.hotelmanager.rooms.service.impl;

import com.devfrank.hotelmanager.rooms.dto.RoomDTO;
import com.devfrank.hotelmanager.rooms.dto.filter.RoomCriteria;
import com.devfrank.hotelmanager.rooms.dto.request.SaveRoomRequest;
import com.devfrank.hotelmanager.rooms.dto.request.UpdateRoomStatusRequest;
import com.devfrank.hotelmanager.rooms.entity.Room;
import com.devfrank.hotelmanager.rooms.repository.RoomRepository;
import com.devfrank.hotelmanager.rooms.service.RoomService;
import com.devfrank.hotelmanager.rooms.util.enums.RoomStatus;
import com.devfrank.hotelmanager.rooms.util.mapper.RoomMapper;
import com.devfrank.hotelmanager.rooms.util.specs.RoomSpecs;
import com.devfrank.hotelmanager.security.util.SecurityUtils;
import com.devfrank.hotelmanager.shared.constans.ErrorConstants;
import com.devfrank.hotelmanager.shared.constans.PermissionsConstants;
import com.devfrank.hotelmanager.shared.constans.ResourceConstants;
import com.devfrank.hotelmanager.shared.constans.UtilConstants;
import com.devfrank.hotelmanager.shared.exception.BusinessException;
import com.devfrank.hotelmanager.shared.exception.DeactivatedResourceException;
import com.devfrank.hotelmanager.shared.exception.ResourceNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class RoomServiceImpl implements RoomService {

    private final RoomRepository roomRepository;
    private final RoomMapper roomMapper;
    private final SecurityUtils securityUtils;

    @Override
    public RoomDTO create(SaveRoomRequest request) {
        boolean canViewInactive = securityUtils.hasPermission(PermissionsConstants.HABITACIONES_VER_INACTIVOS);

        if (!canViewInactive) {
            Optional<Room> roomOpt = roomRepository.findByNumber(request.number());

            if (roomOpt.isPresent() && !roomOpt.get().getIsActive()) {
                throw new DeactivatedResourceException(
                        ErrorConstants.ROOM_ALREADY_EXISTS_INACTIVE,
                        roomOpt.get().getId().toString()
                );
            }
        }

        Room room = roomMapper.toEntity(request);
        return roomMapper.toDTO(roomRepository.save(room));
    }

    @Override
    public void deactivate(UUID id) {
        Room room = roomRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException(ResourceConstants.ROOM, id.toString()));

        // TODO Antes de inactivar, verificar si tiene reservas pendientes o confirmadas.
        // TODO Si tiene pendientes, entonces enviar excepción indicando que no se puede inactivar y la razón.
        // TODO Si no tiene pendientes, se procede a inactivar la habitación.

        room.setIsActive(Boolean.FALSE);
        roomRepository.save(room);
    }

    @Override
    public void reactivate(UUID id) {
        Room room = roomRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException(ResourceConstants.ROOM, id.toString()));
        room.setIsActive(Boolean.TRUE);
        roomRepository.save(room);
    }

    @Override
    public Page<RoomDTO> findAllBy(RoomCriteria filter, Pageable pageable) {
        Specification<Room> spec = RoomSpecs.filter(filter);
        boolean canViewInactive = securityUtils.hasPermission(PermissionsConstants.HABITACIONES_VER_INACTIVOS);

        if (filter.getIsActive() == null) {
            filter.setIsActive(canViewInactive
                    ? UtilConstants.IS_ACTIVE_FILTER_ALL
                    : UtilConstants.IS_ACTIVE_FILTER_TRUE);
        }

        if (filter.getIsActive().equals(UtilConstants.IS_ACTIVE_FILTER_ALL) && !canViewInactive) {
            throw new AccessDeniedException(ErrorConstants.ROOM_FORBIDDEN_INACTIVE_VIEW);
        }

        return roomRepository.findAll(spec, pageable).map(roomMapper::toDTO);
    }

    @Override
    public RoomDTO findById(UUID id) {
        Room room = roomRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException(ResourceConstants.ROOM, id.toString()));

        boolean canViewInactive = securityUtils.hasPermission(PermissionsConstants.HABITACIONES_VER_INACTIVOS);

        if (!room.getIsActive() && !canViewInactive) {
            throw new ResourceNotFoundException(ErrorConstants.ROOM_ALREADY_EXISTS_INACTIVE, room.getId().toString());
        }

        return roomMapper.toDTO(room);
    }

    @Override
    public RoomDTO update(UUID id, SaveRoomRequest request) {
        Room room = roomRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException(ResourceConstants.ROOM, id.toString()));

        if (!room.getIsActive()) {
            throw new BusinessException(ErrorConstants.ROOM_UPDATE_INACTIVE_PROHIBITED);
        }

        roomMapper.updateEntity(request, room);
        return roomMapper.toDTO(roomRepository.save(room));
    }

    @Override
    public void updateStatus(UUID id, UpdateRoomStatusRequest request) {
        Room room = roomRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException(ResourceConstants.ROOM, id.toString()));

        if (!room.getIsActive()) {
            throw new BusinessException(ErrorConstants.ROOM_STATUS_UPDATE_INACTIVE_PROHIBITED);
        }

        RoomStatus roomStatusRequest = RoomStatus.fromStatus(request.status());

        if (room.getStatus() == RoomStatus.MANTENIMIENTO && roomStatusRequest != RoomStatus.DISPONIBLE) {
            throw new BusinessException(ErrorConstants.ROOM_MAINTENANCE_ONLY_AVAILABLE);
        }

        room.setStatus(roomStatusRequest);
        roomRepository.save(room);
    }
}