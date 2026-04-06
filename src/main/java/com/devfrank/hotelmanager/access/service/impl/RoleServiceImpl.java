package com.devfrank.hotelmanager.access.service.impl;

import com.devfrank.hotelmanager.access.dto.RoleDTO;
import com.devfrank.hotelmanager.access.dto.filter.RoleCriteria;
import com.devfrank.hotelmanager.access.dto.request.SaveRoleRequest;
import com.devfrank.hotelmanager.access.entity.Role;
import com.devfrank.hotelmanager.access.repository.RoleRepository;
import com.devfrank.hotelmanager.access.service.PermissionService;
import com.devfrank.hotelmanager.access.service.RoleService;
import com.devfrank.hotelmanager.access.service.api.RoleUsageValidator;
import com.devfrank.hotelmanager.access.util.mapper.RoleMapper;
import com.devfrank.hotelmanager.access.util.specs.RoleSpecs;
import com.devfrank.hotelmanager.shared.constans.ResourceConstants;
import com.devfrank.hotelmanager.shared.exception.BusinessException;
import com.devfrank.hotelmanager.shared.exception.ResourceIDsNotFoundException;
import com.devfrank.hotelmanager.shared.exception.ResourceNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class RoleServiceImpl implements RoleService {

    private final RoleRepository roleRepository;
    private final RoleMapper roleMapper;
    private final PermissionService permissionService;
    private final List<RoleUsageValidator> roleUsageValidators;

    @Override
    public RoleDTO create(SaveRoleRequest request) {
        List<UUID> ids = request.permissions().stream().map(UUID::fromString).toList();
        permissionService.ensureAllExist(ids);
        Role role = roleMapper.toEntity(request);
        return roleMapper.toDTO(roleRepository.save(role));
    }

    @Override
    public void deactivate(UUID id) {
        Role role = roleRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException(ResourceConstants.ROLE, id.toString()));
        StringBuilder errorDetail = new StringBuilder();

        for (RoleUsageValidator validator : roleUsageValidators) {
            List<String> identities = validator.getUsageIdentities(id);
            if (!identities.isEmpty()) {
                if (!errorDetail.isEmpty()) errorDetail.append("; ");
                errorDetail.append(String.format("asignado a los siguientes %s: %s",
                        validator.getResourceName().toLowerCase(),
                        String.join(", ", identities)));
            }
        }

        if (!errorDetail.isEmpty()) {
            throw new BusinessException("No se puede inactivar el rol porque está " + errorDetail);
        }

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
        return roleRepository.findAll(spec, pageable)
                .map(roleMapper::toDTO);
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
        permissionService.ensureAllExist(request.permissions().stream().map(UUID::fromString).toList());
        roleMapper.updateEntity(request, role);
        return roleMapper.toDTO(roleRepository.save(role));
    }

    @Override
    public void validateExists(UUID id) {
        boolean roleExist = roleRepository.existsById(id);
        if (!roleExist) {
            throw new ResourceIDsNotFoundException(
                    "El siguiente ID de rol no existe: " + id
            );
        }
    }
}