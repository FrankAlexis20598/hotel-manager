package com.devfrank.hotelmanager.access.service;

import com.devfrank.hotelmanager.access.dto.PermissionItemDTO;

import java.util.List;
import java.util.Map;

public interface PermissionService {
    Map<String, List<PermissionItemDTO>> getGroupedByModule();
}