package com.devfrank.hotelmanager.access.service;

import com.devfrank.hotelmanager.access.dto.PermissionDTO;

import java.util.List;
import java.util.Map;
import java.util.UUID;

public interface PermissionService {
    Map<String, List<PermissionDTO>> getGroupedByModule();

    List<PermissionDTO> findByIdIn(List<UUID> ids);
}