package com.devfrank.hotelmanager.access.repository;

import com.devfrank.hotelmanager.access.entity.Role;
import com.devfrank.hotelmanager.shared.base.BaseRepository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface RoleRepository extends BaseRepository<Role, UUID> {
    List<Role> findAllByIsActiveTrue();

    Optional<Role> findByIdAndIsActiveTrue(UUID id);

    Optional<Role> findByNameAndIsActiveFalse(String name);
}