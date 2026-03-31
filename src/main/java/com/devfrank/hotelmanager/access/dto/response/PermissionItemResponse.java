package com.devfrank.hotelmanager.access.dto.response;

import com.devfrank.hotelmanager.access.dto.PermissionItemDTO;

public record PermissionItemResponse(
        String id,
        String action
) {
    public static PermissionItemResponse fromDTO(PermissionItemDTO dto) {
        return new PermissionItemResponse(
                dto.id().toString(),
                dto.action()
        );
    }
}