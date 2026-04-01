package com.devfrank.hotelmanager.users.service;

import com.devfrank.hotelmanager.shared.base.BaseService;
import com.devfrank.hotelmanager.users.dto.AppUserDTO;
import com.devfrank.hotelmanager.users.dto.command.CreateAppUserCommand;
import com.devfrank.hotelmanager.users.dto.command.UpdateAppUserCommand;

import java.util.UUID;

public interface AppUserService extends BaseService<CreateAppUserCommand, UpdateAppUserCommand, AppUserDTO, UUID> {
}