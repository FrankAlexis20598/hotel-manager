package com.devfrank.hotelmanager.users.service.impl;

import com.devfrank.hotelmanager.users.dto.AppUserDTO;
import com.devfrank.hotelmanager.users.dto.command.CreateAppUserCommand;
import com.devfrank.hotelmanager.users.dto.command.UpdateAppUserCommand;
import com.devfrank.hotelmanager.users.entity.AppUser;
import com.devfrank.hotelmanager.users.repository.AppUserRepository;
import com.devfrank.hotelmanager.users.service.AppUserService;
import com.devfrank.hotelmanager.users.util.exception.AppUserNotFoundException;
import com.devfrank.hotelmanager.users.util.mapper.AppUserMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class AppUserServiceImpl implements AppUserService {

    private final AppUserRepository appUserRepository;
    private final AppUserMapper appUserMapper;

    @Override
    public List<AppUserDTO> findAll() {
        return appUserRepository.findAll().stream()
                .map(appUserMapper::toDTO)
                .collect(Collectors.toList());
    }

    @Override
    public AppUserDTO findById(UUID id) {
        return appUserRepository.findById(id)
                .map(appUserMapper::toDTO)
                .orElseThrow(() -> new AppUserNotFoundException(id));
    }

    @Override
    public AppUserDTO create(CreateAppUserCommand command) {
        AppUser user = appUserMapper.toEntity(command);
        return appUserMapper.toDTO(appUserRepository.save(user));
    }

    @Override
    public AppUserDTO update(UUID id, UpdateAppUserCommand command) {
        AppUser user = appUserRepository.findById(id)
                .orElseThrow(() -> new AppUserNotFoundException(id));
        appUserMapper.toEntity(user, command);
        return appUserMapper.toDTO(appUserRepository.save(user));
    }

    @Override
    public void delete(UUID id) {
        AppUser user = appUserRepository.findById(id)
                .orElseThrow(() -> new AppUserNotFoundException(id));
        user.setIsActive(false);
        appUserRepository.save(user);
    }
}