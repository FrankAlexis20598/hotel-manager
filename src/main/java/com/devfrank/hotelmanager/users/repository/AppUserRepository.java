package com.devfrank.hotelmanager.users.repository;

import com.devfrank.hotelmanager.shared.base.repository.BaseRepository;
import com.devfrank.hotelmanager.users.entity.AppUser;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;
import java.util.UUID;

public interface AppUserRepository extends BaseRepository<AppUser, UUID> {
    Optional<AppUser> findByEmail(String email);

    @Query("SELECT u FROM AppUser u " +
           "LEFT JOIN FETCH u.role r " +
           "LEFT JOIN FETCH r.permissions " +
           "WHERE u.email = :email")
    Optional<AppUser> findByEmailWithRoleAndPermissions(@Param("email") String email);

    Optional<AppUser> findByResetPasswordToken(String token);
}