package com.devfrank.hotelmanager.access.service.impl;

import com.devfrank.hotelmanager.access.dto.PermissionDTO;
import com.devfrank.hotelmanager.access.dto.RoleDTO;
import com.devfrank.hotelmanager.access.dto.filter.RoleCriteria;
import com.devfrank.hotelmanager.access.dto.request.SaveRoleRequest;
import com.devfrank.hotelmanager.access.entity.Role;
import com.devfrank.hotelmanager.access.repository.RoleRepository;
import com.devfrank.hotelmanager.access.service.PermissionService;
import com.devfrank.hotelmanager.access.service.RoleService;
import com.devfrank.hotelmanager.access.util.mapper.RoleMapper;
import com.devfrank.hotelmanager.access.util.specs.RoleSpecs;
import com.devfrank.hotelmanager.shared.constans.ResourceConstants;
import com.devfrank.hotelmanager.shared.exception.ResourceIDsNotFoundException;
import com.devfrank.hotelmanager.shared.exception.ResourceNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Set;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class RoleServiceImpl implements RoleService {

    private final RoleRepository roleRepository;
    private final RoleMapper roleMapper;
    private final PermissionService permissionService;

    @Override
    public RoleDTO create(SaveRoleRequest request) {
        List<UUID> ids = request.permissions().stream().map(UUID::fromString).toList();
        List<PermissionDTO> permissions = permissionService.findByIdIn(ids);

        if (permissions.size() != ids.size()) {
            Set<UUID> foundIds = permissions.stream()
                    .map(PermissionDTO::id)
                    .collect(Collectors.toSet());

            List<UUID> notFoundIds = ids.stream()
                    .filter(id -> !foundIds.contains(id))
                    .toList();

            throw new ResourceIDsNotFoundException(
                    "Los siguientes IDs de permisos no existen: " + notFoundIds
            );
        }

        Role role = roleMapper.toEntity(request);
        return roleMapper.toDTO(roleRepository.save(role));
    }

    @Override
    public void deactivate(UUID id) {
        Role role = roleRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException(ResourceConstants.ROLE, id.toString()));

        // TODO Antes de inactivar, verificar si algún usuario tiene asignado ese rol.
        // TODO Si está asignado, entonces enviar excepción indicando que no se puede inactivar y la razón.
        // TODO Si no está asignado, se procede a inactivar el rol.

        role.setIsActive(Boolean.FALSE);
        roleRepository.save(role);
    }

    @Override
    public void reactivate(UUID id) {
        Role role = roleRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException(ResourceConstants.ROLE, id.toString()));
        role.setIsActive(Boolean.TRUE);
        roleRepository.save(role);
    }

    @Override
    public Page<RoleDTO> findAllBy(RoleCriteria filter, Pageable pageable) {
        Specification<Role> spec = RoleSpecs.filter(filter);
        return roleRepository.findAll(spec, pageable).map(roleMapper::toDTO);
    }

    @Override
    public RoleDTO findById(UUID id) {
        Role role = roleRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException(ResourceConstants.ROLE, id.toString()));
        return roleMapper.toDTO(role);
    }

    @Override
    public RoleDTO update(UUID id, SaveRoleRequest request) {
        Role role = roleRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException(ResourceConstants.ROLE, id.toString()));
        roleMapper.updateEntity(request, role);
        return roleMapper.toDTO(roleRepository.save(role));
    }
}