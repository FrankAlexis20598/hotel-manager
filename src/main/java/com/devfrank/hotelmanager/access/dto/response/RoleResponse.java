package com.devfrank.hotelmanager.access.dto.response;

import com.devfrank.hotelmanager.access.dto.RoleDTO;

import java.util.List;

public record RoleResponse(
        String id,
        String name,
        String description,
        List<PermissionResponse> permissions
) {
    public static RoleResponse fromDTO(RoleDTO dto) {
        return new RoleResponse(
                dto.id().toString(),
                dto.name(),
                dto.description(),
                dto.permissions().stream()
                        .map(PermissionResponse::fromDTO)
                        .toList()
        );
    }
}