package com.devfrank.hotelmanager.users.dto.response;

import com.devfrank.hotelmanager.users.dto.AppUserDTO;
import com.fasterxml.jackson.annotation.JsonInclude;

@JsonInclude(JsonInclude.Include.NON_NULL)
public record AppUserResponse(
        String id,
        String email,
        Boolean isActive,
        RoleResponse role
) {
    public record RoleResponse(
            String id,
            String name
    ) {
    }

    public static AppUserResponse fromDTO(AppUserDTO dto) {
        return new AppUserResponse(
                dto.id().toString(),
                dto.email(),
                dto.isActive(),
                new RoleResponse(
                        dto.role().id().toString(),
                        dto.role().name()
                )
        );
    }
}