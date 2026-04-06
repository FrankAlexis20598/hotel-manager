package com.devfrank.hotelmanager.access.util.mapper;

import com.devfrank.hotelmanager.access.dto.RoleDTO;
import com.devfrank.hotelmanager.access.dto.request.SaveRoleRequest;
import com.devfrank.hotelmanager.access.entity.Role;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.Named;

import java.util.UUID;

@Mapper(componentModel = "spring", uses = {PermissionMapper.class})
public interface RoleMapper {
    @Mapping(target = "permissions", qualifiedByName = "toDTO")
    RoleDTO toDTO(Role role);

    @Mapping(target = "id", expression = "java(defaultValueForId())")
    @Mapping(target = "isActive", expression = "java(defaultValueForIsActive())")
    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "createdBy", ignore = true)
    @Mapping(target = "updatedAt", ignore = true)
    @Mapping(target = "updatedBy", ignore = true)
    @Mapping(target = "permissions", ignore = true)
    Role toEntity(SaveRoleRequest request);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "isActive", ignore = true)
    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "createdBy", ignore = true)
    @Mapping(target = "updatedAt", ignore = true)
    @Mapping(target = "updatedBy", ignore = true)
    @Mapping(target = "permissions", ignore = true)
    void updateEntity(SaveRoleRequest request, @MappingTarget Role role);

    @Named("defaultValueForId")
    default UUID defaultValueForId() {
        return UUID.randomUUID();
    }

    @Named("defaultValueForIsActive")
    default boolean defaultValueForIsActive() {
        return Boolean.TRUE;
    }
}