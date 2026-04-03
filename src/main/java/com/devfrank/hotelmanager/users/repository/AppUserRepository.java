package com.devfrank.hotelmanager.users.repository;

import com.devfrank.hotelmanager.shared.base.BaseRepository;
import com.devfrank.hotelmanager.users.entity.AppUser;

import java.util.Optional;
import java.util.UUID;

public interface AppUserRepository extends BaseRepository<AppUser, UUID> {
    Optional<AppUser> findByEmail(String email);
}