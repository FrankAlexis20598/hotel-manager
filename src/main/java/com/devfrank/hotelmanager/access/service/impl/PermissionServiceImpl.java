package com.devfrank.hotelmanager.access.service.impl;

import com.devfrank.hotelmanager.access.dto.PermissionDTO;
import com.devfrank.hotelmanager.access.entity.Permission;
import com.devfrank.hotelmanager.access.repository.PermissionRepository;
import com.devfrank.hotelmanager.access.service.PermissionService;
import com.devfrank.hotelmanager.access.util.mapper.PermissionMapper;
import com.devfrank.hotelmanager.shared.exception.ResourceIDsNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class PermissionServiceImpl implements PermissionService {

    private final PermissionRepository permissionRepository;
    private final PermissionMapper permissionMapper;

    @Override
    public Map<String, List<PermissionDTO>> getGroupedByModule() {
        return permissionRepository.findAll()
                .stream()
                .collect(Collectors.groupingBy(Permission::getModule,
                        Collectors.mapping(permissionMapper::toSummaryDTO, Collectors.toList())
                ));
    }

    @Override
    public void ensureAllExist(List<UUID> ids) {
        List<Permission> permissions = permissionRepository.findByIdIn(ids);

        if (permissions.size() != ids.size()) {
            Set<UUID> foundIds = permissions.stream()
                    .map(Permission::getId)
                    .collect(Collectors.toSet());

            List<UUID> notFoundIds = ids.stream()
                    .filter(id -> !foundIds.contains(id))
                    .toList();

            throw new ResourceIDsNotFoundException(
                    "Los siguientes IDs de permisos no existen: " + notFoundIds
            );
        }
    }
}