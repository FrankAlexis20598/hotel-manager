package com.devfrank.hotelmanager.access.dto.response;

import com.devfrank.hotelmanager.access.dto.PermissionDTO;
import com.fasterxml.jackson.annotation.JsonInclude;

@JsonInclude(JsonInclude.Include.NON_NULL)
public record PermissionResponse(
        String id,
        String module,
        String action,
        String description
) {
    public static PermissionResponse fromDTO(PermissionDTO dto) {
        return new PermissionResponse(
                dto.id().toString(),
                dto.module(),
                dto.action(),
                dto.description()
        );
    }
}