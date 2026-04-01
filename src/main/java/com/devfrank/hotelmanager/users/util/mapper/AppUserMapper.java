package com.devfrank.hotelmanager.users.util.mapper;

import com.devfrank.hotelmanager.access.util.mapper.RoleSummaryMapper;
import com.devfrank.hotelmanager.shared.base.CrudMapper;
import com.devfrank.hotelmanager.users.dto.AppUserDTO;
import com.devfrank.hotelmanager.users.dto.command.CreateAppUserCommand;
import com.devfrank.hotelmanager.users.dto.command.UpdateAppUserCommand;
import com.devfrank.hotelmanager.users.entity.AppUser;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.UUID;

@Component
@RequiredArgsConstructor
public class AppUserMapper implements CrudMapper<AppUser, AppUserDTO, CreateAppUserCommand, UpdateAppUserCommand> {

    private final RoleSummaryMapper roleSummaryMapper;

    @Override
    public AppUser toEntity(CreateAppUserCommand command) {
        AppUser appUser = new AppUser();
        appUser.setId(UUID.randomUUID());
        appUser.setEmail(command.email());
        appUser.setPassword(command.password()); // TODO usar Bcrypt
        appUser.setIsActive(Boolean.TRUE);
        appUser.setRole(roleSummaryMapper.fromUUID(command.roleId()));
        return appUser;
    }

    @Override
    public void toEntity(AppUser entity, UpdateAppUserCommand command) {
        entity.setEmail(command.email());
        entity.setPassword(command.password());
        entity.setIsActive(command.isActive());
        entity.setRole(roleSummaryMapper.fromUUID(command.roleId()));
    }

    @Override
    public AppUserDTO toDTO(AppUser entity) {
        return new AppUserDTO(
                entity.getId(),
                entity.getEmail(),
                entity.getPassword(),
                entity.getIsActive(),
                roleSummaryMapper.toDTO(entity.getRole())
        );
    }
}