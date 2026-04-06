package com.devfrank.hotelmanager.access.util.mapper;

import com.devfrank.hotelmanager.access.dto.RoleDTO;
import com.devfrank.hotelmanager.access.dto.request.SaveRoleRequest;
import com.devfrank.hotelmanager.access.entity.Role;
import org.mapstruct.BeanMapping;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.Named;

import java.util.UUID;

@Mapper(componentModel = "spring", uses = {PermissionMapper.class})
public interface RoleMapper {
    @Named("toDTO")
    @Mapping(target = "permissions", qualifiedByName = "toDTO")
    RoleDTO toDTO(Role role);

    @Named("toSummaryDTO")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    @Mapping(target = "name", source = "name")
    RoleDTO toSummaryDTO(Role role);

    @Mapping(target = "id", expression = "java(defaultValueForId())")
    @Mapping(target = "isActive", expression = "java(defaultValueForIsActive())")
    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "createdBy", ignore = true)
    @Mapping(target = "updatedAt", ignore = true)
    @Mapping(target = "updatedBy", ignore = true)
    @Mapping(target = "permissions", source = "permissions", qualifiedByName = "idToEntity")
    Role toEntity(SaveRoleRequest request);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "isActive", ignore = true)
    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "createdBy", ignore = true)
    @Mapping(target = "updatedAt", ignore = true)
    @Mapping(target = "updatedBy", ignore = true)
    @Mapping(target = "permissions", source = "permissions", qualifiedByName = "idToEntity")
    void updateEntity(SaveRoleRequest request, @MappingTarget Role role);

    @Named("defaultValueForId")
    default UUID defaultValueForId() {
        return UUID.randomUUID();
    }

    @Named("defaultValueForIsActive")
    default boolean defaultValueForIsActive() {
        return Boolean.TRUE;
    }

    @Named("idToEntity")
    default Role idToEntity(UUID id) {
        if (id == null) return null;
        return new Role(id);
    }
}