package com.devfrank.hotelmanager.users.util.mapper;

import com.devfrank.hotelmanager.access.util.mapper.RoleMapper;
import com.devfrank.hotelmanager.users.dto.AppUserDTO;
import com.devfrank.hotelmanager.users.dto.request.SaveAppUserRequest;
import com.devfrank.hotelmanager.users.entity.AppUser;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.Named;

import java.util.UUID;

@Mapper(componentModel = "spring", uses = {RoleMapper.class})
public interface AppUserMapper {

    @Mapping(target = "role", qualifiedByName = "toSummaryDTO")
    AppUserDTO toDTO(AppUser appUser);

    @Mapping(target = "id", expression = "java(defaultValueForId())")
    @Mapping(target = "isActive", expression = "java(defaultValueForIsActive())")
    @Mapping(target = "resetPasswordToken", ignore = true)
    @Mapping(target = "resetPasswordExpiresAt", ignore = true)
    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "createdBy", ignore = true)
    @Mapping(target = "updatedAt", ignore = true)
    @Mapping(target = "updatedBy", ignore = true)
    @Mapping(target = "role", source = "roleId", qualifiedByName = "idToEntity")
    AppUser toEntity(SaveAppUserRequest saveAppUserRequest);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "password", ignore = true)
    @Mapping(target = "isActive", ignore = true)
    @Mapping(target = "resetPasswordToken", ignore = true)
    @Mapping(target = "resetPasswordExpiresAt", ignore = true)
    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "createdBy", ignore = true)
    @Mapping(target = "updatedAt", ignore = true)
    @Mapping(target = "updatedBy", ignore = true)
    @Mapping(target = "role", source = "roleId", qualifiedByName = "idToEntity")
    void updateEntity(SaveAppUserRequest saveAppUserRequest, @MappingTarget AppUser appUser);

    @Named("defaultValueForId")
    default UUID defaultValueForId() {
        return UUID.randomUUID();
    }

    @Named("defaultValueForIsActive")
    default boolean defaultValueForIsActive() {
        return Boolean.TRUE;
    }
}