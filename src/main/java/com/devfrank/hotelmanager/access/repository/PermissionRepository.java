package com.devfrank.hotelmanager.access.repository;

import com.devfrank.hotelmanager.access.entity.Permission;
import com.devfrank.hotelmanager.shared.base.BaseRepository;

import java.util.UUID;

public interface PermissionRepository extends BaseRepository<Permission, UUID> {
}