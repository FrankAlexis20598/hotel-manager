package com.devfrank.hotelmanager.access.service;

import com.devfrank.hotelmanager.access.dto.RoleDTO;
import com.devfrank.hotelmanager.access.dto.command.SaveRoleCommand;
import com.devfrank.hotelmanager.shared.base.BaseService;

import java.util.UUID;

public interface RoleService extends BaseService<SaveRoleCommand, SaveRoleCommand, RoleDTO, UUID> {
}