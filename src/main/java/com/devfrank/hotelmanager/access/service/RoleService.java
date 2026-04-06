package com.devfrank.hotelmanager.access.service;

import com.devfrank.hotelmanager.access.dto.RoleDTO;
import com.devfrank.hotelmanager.access.dto.filter.RoleCriteria;
import com.devfrank.hotelmanager.access.dto.request.SaveRoleRequest;
import com.devfrank.hotelmanager.shared.base.service.CreateService;
import com.devfrank.hotelmanager.shared.base.service.LifecycleService;
import com.devfrank.hotelmanager.shared.base.service.ReadService;
import com.devfrank.hotelmanager.shared.base.service.UpdateService;

import java.util.UUID;

public interface RoleService extends
        ReadService<RoleDTO, UUID, RoleCriteria>,
        CreateService<SaveRoleRequest, RoleDTO>,
        UpdateService<UUID, SaveRoleRequest, RoleDTO>,
        LifecycleService<UUID> {
    void validateExists(UUID id);
}