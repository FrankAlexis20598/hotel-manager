package com.devfrank.hotelmanager.users.dto.response;

import com.devfrank.hotelmanager.users.dto.AppUserDTO;

public record AppUserResponse(
        String id,
        String email,
        boolean isActive,
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