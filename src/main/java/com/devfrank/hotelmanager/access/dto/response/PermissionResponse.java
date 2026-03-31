package com.devfrank.hotelmanager.access.dto.response;

import com.devfrank.hotelmanager.access.dto.PermissionDTO;

public record PermissionResponse(
        String id,
        String module,
        String action
) {
    public static PermissionResponse fromDTO(PermissionDTO dto) {
        return new PermissionResponse(
                dto.id().toString(),
                dto.module(),
                dto.action()
        );
    }
}