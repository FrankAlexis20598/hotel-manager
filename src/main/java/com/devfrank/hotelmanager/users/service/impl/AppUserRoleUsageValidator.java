package com.devfrank.hotelmanager.users.service.impl;

import com.devfrank.hotelmanager.access.service.api.RoleUsageValidator;
import com.devfrank.hotelmanager.shared.constans.ResourceConstants;
import com.devfrank.hotelmanager.users.entity.AppUser;
import com.devfrank.hotelmanager.users.repository.AppUserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.UUID;

@Component
@RequiredArgsConstructor
public class AppUserRoleUsageValidator implements RoleUsageValidator {

    private final AppUserRepository appUserRepository;

    @Override
    public List<String> getUsageIdentities(UUID roleId) {
        return appUserRepository.findAllByRole_Id(roleId)
                .stream()
                .map(AppUser::getEmail)
                .toList();
    }

    @Override
    public String getResourceName() {
        return ResourceConstants.USER;
    }
}