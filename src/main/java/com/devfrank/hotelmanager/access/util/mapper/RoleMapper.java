package com.devfrank.hotelmanager.access.util.mapper;

import com.devfrank.hotelmanager.access.dto.RoleDTO;
import com.devfrank.hotelmanager.access.dto.command.SaveRoleCommand;
import com.devfrank.hotelmanager.access.entity.Role;
import com.devfrank.hotelmanager.shared.base.CrudMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.UUID;

@Component
@RequiredArgsConstructor
public class RoleMapper implements CrudMapper<Role, RoleDTO, SaveRoleCommand, SaveRoleCommand> {

    private final PermissionMapper permissionMapper;

    @Override
    public RoleDTO toDTO(Role entity) {
        return new RoleDTO(
                entity.getId(),
                entity.getName(),
                entity.getDescription(),
                permissionMapper.toDTOs(entity.getPermissions())
        );
    }

    @Override
    public Role toEntity(SaveRoleCommand command) {
        Role role = new Role();
        role.setId(UUID.randomUUID());
        role.setName(command.name());
        role.setDescription(command.description());
        role.setIsActive(Boolean.TRUE);
        role.setPermissions(permissionMapper.fromListUUIDs(command.permissions()));
        return role;
    }

    @Override
    public void toEntity(Role entity, SaveRoleCommand command) {
        entity.setName(command.name());
        entity.setDescription(command.description());
        entity.getPermissions().clear();
        entity.getPermissions().addAll(permissionMapper.fromListUUIDs(command.permissions()));
    }
}