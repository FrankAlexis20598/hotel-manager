package com.devfrank.hotelmanager.access.repository;

import com.devfrank.hotelmanager.access.entity.Permission;
import com.devfrank.hotelmanager.shared.base.repository.BaseRepository;

import java.util.List;
import java.util.UUID;

public interface PermissionRepository extends BaseRepository<Permission, UUID> {
    List<Permission> findByIdIn(List<UUID> ids);
}