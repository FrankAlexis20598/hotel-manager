package com.devfrank.hotelmanager.access.util.mapper;

import com.devfrank.hotelmanager.access.dto.PermissionDTO;
import com.devfrank.hotelmanager.access.entity.Permission;
import org.mapstruct.BeanMapping;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;

import java.util.UUID;

@Mapper(componentModel = "spring")
public interface PermissionMapper {
    @Named("toDTO")
    PermissionDTO toDTO(Permission permission);

    @Named("toSummaryDTO")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    @Mapping(target = "action", source = "action")
    @Mapping(target = "description", source = "description")
    PermissionDTO toSummaryDTO(Permission permission);

    @Mapping(target = "id", source = "id")
    @Mapping(target = "module", ignore = true)
    @Mapping(target = "action", ignore = true)
    @Mapping(target = "description", ignore = true)
    @Mapping(target = "createdAt", ignore = true)
    Permission fromId(UUID id);
}