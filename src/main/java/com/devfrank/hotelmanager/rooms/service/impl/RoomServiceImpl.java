package com.devfrank.hotelmanager.rooms.service.impl;

import com.devfrank.hotelmanager.rooms.dto.RoomDTO;
import com.devfrank.hotelmanager.rooms.dto.filter.RoomCriteria;
import com.devfrank.hotelmanager.rooms.dto.request.SaveRoomRequest;
import com.devfrank.hotelmanager.rooms.entity.Room;
import com.devfrank.hotelmanager.rooms.repository.RoomRepository;
import com.devfrank.hotelmanager.rooms.service.RoomService;
import com.devfrank.hotelmanager.rooms.service.api.RoomReservationValidator;
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
import com.devfrank.hotelmanager.shared.util.PaginationUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class RoomServiceImpl implements RoomService {

    private final RoomRepository roomRepository;
    private final RoomMapper roomMapper;
    private final SecurityUtils securityUtils;
    private final RoomReservationValidator roomReservationValidator;

    @Override
    public RoomDTO create(SaveRoomRequest request) {
        validateRoomNumberUniqueness(request.number());
        Room room = roomMapper.toEntity(request);
        return roomMapper.toDTO(roomRepository.save(room));
    }

    @Override
    public void deactivate(UUID id) {
        Room room = findRoomById(id);

        if (room.getStatus() == RoomStatus.OCUPADA) {
            throw new BusinessException(String.format(ErrorConstants.ROOM_DEACTIVATE_IS_OCCUPIED,
                    RoomStatus.OCUPADA));
        }

        if (roomReservationValidator.hasActiveOrFutureReservations(id)) {
            throw new BusinessException(ErrorConstants.ROOM_DEACTIVATE_HAS_RESERVATIONS);
        }

        room.setIsActive(Boolean.FALSE);
        roomRepository.save(room);
    }

    @Override
    public void reactivate(UUID id) {
        Room room = findRoomById(id);

        if (room.getIsActive()) {
            throw new BusinessException(ErrorConstants.ROOM_ALREADY_ACTIVE);
        }

        room.setIsActive(Boolean.TRUE);
        room.setStatus(RoomStatus.MANTENIMIENTO);
        roomRepository.save(room);
    }

    @Override
    public Page<RoomDTO> findAllBy(RoomCriteria filter, Pageable pageable) {
        applySecurityToFilter(filter);
        pageable = PaginationUtils.ensureSort(pageable, UtilConstants.CREATE_AT_ATTRIBUTE, Sort.Direction.DESC);
        Specification<Room> spec = RoomSpecs.filter(filter);
        return roomRepository.findAll(spec, pageable)
                .map(roomMapper::toDTO);
    }

    @Override
    public RoomDTO findById(UUID id) {
        Room room = findRoomById(id);
        checkRoomVisibility(room);
        return roomMapper.toDTO(room);
    }

    @Override
    public RoomDTO update(UUID id, SaveRoomRequest request) {
        Room room = findRoomById(id);

        if (!room.getIsActive()) {
            throw new BusinessException(ErrorConstants.ROOM_UPDATE_INACTIVE_PROHIBITED);
        }

        if (!room.getNumber().equals(request.number())) {
            validateRoomNumberUniqueness(request.number());
        }

        roomMapper.updateEntity(request, room);
        return roomMapper.toDTO(roomRepository.save(room));
    }

    @Override
    public void putInMaintenance(UUID id) {
        Room room = findActiveRoom(id);
        if (room.getStatus() != RoomStatus.DISPONIBLE) {
            throw new BusinessException(ErrorConstants.ROOM_ONLY_AVAILABLE_TO_MAINTENANCE);
        }
        room.setStatus(RoomStatus.MANTENIMIENTO);
        roomRepository.save(room);
    }

    @Override
    public void finishMaintenance(UUID id) {
        Room room = findActiveRoom(id);
        if (room.getStatus() != RoomStatus.MANTENIMIENTO) {
            throw new BusinessException(ErrorConstants.ROOM_NOT_IN_MAINTENANCE);
        }
        room.setStatus(RoomStatus.LIMPIEZA);
        roomRepository.save(room);
    }

    @Override
    public void startCleaning(UUID id) {
        Room room = findActiveRoom(id);
        if (room.getStatus() != RoomStatus.DISPONIBLE) {
            throw new BusinessException(ErrorConstants.ROOM_ONLY_AVAILABLE_TO_CLEANING);
        }
        room.setStatus(RoomStatus.LIMPIEZA);
        roomRepository.save(room);
    }

    @Override
    public void markAsReady(UUID id) {
        Room room = findActiveRoom(id);
        if (room.getStatus() != RoomStatus.LIMPIEZA) {
            throw new BusinessException(ErrorConstants.ROOM_NOT_IN_CLEANING);
        }
        room.setStatus(RoomStatus.DISPONIBLE);
        roomRepository.save(room);
    }

    private void validateRoomNumberUniqueness(String number) {
        roomRepository.findByNumber(number).ifPresent(room -> {
            if (room.getIsActive()) {
                throw new BusinessException(ErrorConstants.ROOM_NUMBER_ALREADY_EXISTS);
            }

            boolean canViewInactive = securityUtils.hasPermission(PermissionsConstants.HABITACIONES_VER_INACTIVOS);

            if (canViewInactive) {
                throw new BusinessException(ErrorConstants.ROOM_ALREADY_EXISTS_INACTIVE_RECOVERABLE);
            } else {
                throw new DeactivatedResourceException(ErrorConstants.ROOM_ALREADY_EXISTS_INACTIVE, room.getId().toString());
            }
        });
    }

    private Room findRoomById(UUID id) {
        return roomRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException(ResourceConstants.ROOM, id.toString()));
    }

    private void applySecurityToFilter(RoomCriteria filter) {
        boolean canViewInactive = securityUtils.hasPermission(PermissionsConstants.HABITACIONES_VER_INACTIVOS);

        if (canViewInactive) {
            if (filter.getIsActive() == null) {
                filter.setIsActive(UtilConstants.IS_ACTIVE_FILTER_ALL);
            }
        } else {
            filter.setIsActive(UtilConstants.IS_ACTIVE_FILTER_TRUE);
        }
    }

    private void checkRoomVisibility(Room room) {
        if (!room.getIsActive()) {
            boolean canViewInactive = securityUtils.hasPermission(PermissionsConstants.HABITACIONES_VER_INACTIVOS);
            if (!canViewInactive) {
                throw new ResourceNotFoundException(ResourceConstants.ROOM, room.getId().toString());
            }
        }
    }

    private Room findActiveRoom(UUID id) {
        Room room = findRoomById(id);
        if (!room.getIsActive()) {
            throw new BusinessException(ErrorConstants.ROOM_STATUS_UPDATE_INACTIVE_PROHIBITED);
        }
        return room;
    }
}