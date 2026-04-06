package com.devfrank.hotelmanager.users.service.impl;

import com.devfrank.hotelmanager.access.service.RoleService;
import com.devfrank.hotelmanager.shared.constans.ResourceConstants;
import com.devfrank.hotelmanager.shared.exception.ResourceNotFoundException;
import com.devfrank.hotelmanager.users.dto.AppUserDTO;
import com.devfrank.hotelmanager.users.dto.filter.AppUserCriteria;
import com.devfrank.hotelmanager.users.dto.request.SaveAppUserRequest;
import com.devfrank.hotelmanager.users.entity.AppUser;
import com.devfrank.hotelmanager.users.repository.AppUserRepository;
import com.devfrank.hotelmanager.users.service.AppUserService;
import com.devfrank.hotelmanager.users.util.mapper.AppUserMapper;
import com.devfrank.hotelmanager.users.util.specs.AppUserSpecs;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class AppUserServiceImpl implements AppUserService {

    private final AppUserRepository appUserRepository;
    private final AppUserMapper appUserMapper;
    private final RoleService roleService;

    @Override
    public AppUserDTO create(SaveAppUserRequest request) {
        roleService.validateExists(UUID.fromString(request.roleId()));
        AppUser appUser = appUserMapper.toEntity(request);
        return appUserMapper.toDTO(appUserRepository.save(appUser));
    }

    @Override
    public void deactivate(UUID id) {
        AppUser appUser = appUserRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException(ResourceConstants.USER, id.toString()));
        appUser.setIsActive(Boolean.FALSE);
        appUserRepository.save(appUser);
    }

    @Override
    public void reactivate(UUID id) {
        AppUser appUser = appUserRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException(ResourceConstants.USER, id.toString()));
        appUser.setIsActive(Boolean.TRUE);
        appUserRepository.save(appUser);
    }

    @Override
    public Page<AppUserDTO> findAllBy(AppUserCriteria filter, Pageable pageable) {
        Specification<AppUser> spec = AppUserSpecs.filter(filter);
        return appUserRepository.findAll(spec, pageable)
                .map(appUserMapper::toDTO);
    }

    @Override
    public AppUserDTO findById(UUID id) {
        AppUser appUser = appUserRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException(ResourceConstants.USER, id.toString()));
        return appUserMapper.toDTO(appUser);
    }

    @Override
    public AppUserDTO update(UUID id, SaveAppUserRequest request) {
        AppUser appUser = appUserRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException(ResourceConstants.USER, id.toString()));
        roleService.validateExists(UUID.fromString(request.roleId()));
        appUserMapper.updateEntity(request, appUser);
        return appUserMapper.toDTO(appUserRepository.save(appUser));
    }
}