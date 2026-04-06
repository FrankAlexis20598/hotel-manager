package com.devfrank.hotelmanager.users.service;

import com.devfrank.hotelmanager.shared.base.service.CreateService;
import com.devfrank.hotelmanager.shared.base.service.LifecycleService;
import com.devfrank.hotelmanager.shared.base.service.ReadService;
import com.devfrank.hotelmanager.shared.base.service.UpdateService;
import com.devfrank.hotelmanager.users.dto.AppUserDTO;
import com.devfrank.hotelmanager.users.dto.filter.AppUserCriteria;
import com.devfrank.hotelmanager.users.dto.request.SaveAppUserRequest;

import java.util.UUID;

public interface AppUserService extends
        ReadService<AppUserDTO, UUID, AppUserCriteria>,
        CreateService<SaveAppUserRequest, AppUserDTO>,
        UpdateService<UUID, SaveAppUserRequest, AppUserDTO>,
        LifecycleService<UUID> {
}