package com.devfrank.hotelmanager.rooms.util.mapper;

import com.devfrank.hotelmanager.rooms.dto.RoomDTO;
import com.devfrank.hotelmanager.rooms.dto.request.SaveRoomRequest;
import com.devfrank.hotelmanager.rooms.entity.Room;
import com.devfrank.hotelmanager.rooms.util.enums.RoomStatus;
import com.devfrank.hotelmanager.rooms.util.enums.RoomType;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.Named;

import java.util.UUID;

@Mapper(componentModel = "spring")
public interface RoomMapper {
    RoomDTO toDTO(Room room);

    @Mapping(target = "id", expression = "java(defaultValueForId())")
    @Mapping(target = "type", qualifiedByName = "stringToRoomType")
    @Mapping(target = "status", expression = "java(defaultValueForStatus())")
    @Mapping(target = "isActive", expression = "java(defaultValueForIsActive())")
    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "createdBy", ignore = true)
    @Mapping(target = "updatedAt", ignore = true)
    @Mapping(target = "updatedBy", ignore = true)
    Room toEntity(SaveRoomRequest request);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "type", qualifiedByName = "stringToRoomType")
    @Mapping(target = "status", ignore = true)
    @Mapping(target = "isActive", ignore = true)
    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "createdBy", ignore = true)
    @Mapping(target = "updatedAt", ignore = true)
    @Mapping(target = "updatedBy", ignore = true)
    void updateEntity(SaveRoomRequest request, @MappingTarget Room room);

    @Named("defaultValueForId")
    default UUID defaultValueForId() {
        return UUID.randomUUID();
    }

    @Named("defaultValueForStatus")
    default RoomStatus defaultValueForStatus() {
        return RoomStatus.DISPONIBLE;
    }

    @Named("defaultValueForIsActive")
    default Boolean defaultValueForIsActive() {
        return Boolean.TRUE;
    }

    @Named("stringToRoomType")
    default RoomType stringToRoomType(String type) {
        return RoomType.fromType(type);
    }
}