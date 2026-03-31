package com.devfrank.hotelmanager.access.util.mapper;

import com.devfrank.hotelmanager.access.dto.PermissionDTO;
import com.devfrank.hotelmanager.access.dto.PermissionItemDTO;
import com.devfrank.hotelmanager.access.entity.Permission;
import com.devfrank.hotelmanager.shared.base.BaseMapper;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Component
public class PermissionMapper implements BaseMapper<Permission, PermissionDTO> {

    @Override
    public PermissionDTO toDTO(Permission entity) {
        return new PermissionDTO(
                entity.getId(),
                entity.getModule(),
                entity.getAction()
        );
    }

    public PermissionItemDTO toItemDTO(Permission entity) {
        return new PermissionItemDTO(
                entity.getId(),
                entity.getAction()
        );
    }

    public List<Permission> fromListUUIDs(List<UUID> uuids) {
        return uuids.stream().map(Permission::new).collect(Collectors.toList());
    }
}