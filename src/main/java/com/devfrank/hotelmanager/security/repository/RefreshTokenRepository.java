package com.devfrank.hotelmanager.security.repository;

import com.devfrank.hotelmanager.security.entity.RefreshToken;
import com.devfrank.hotelmanager.users.entity.AppUser;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface RefreshTokenRepository extends JpaRepository<RefreshToken, UUID> {
    Optional<RefreshToken> findByToken(String token);

    @Modifying
    void deleteByUser(AppUser user);
    
    void deleteByToken(String token);
}