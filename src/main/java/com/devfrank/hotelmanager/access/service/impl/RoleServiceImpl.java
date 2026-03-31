package com.devfrank.hotelmanager.access.service.impl;

import com.devfrank.hotelmanager.access.dto.RoleDTO;
import com.devfrank.hotelmanager.access.dto.command.SaveRoleCommand;
import com.devfrank.hotelmanager.access.entity.Role;
import com.devfrank.hotelmanager.access.repository.RoleRepository;
import com.devfrank.hotelmanager.access.service.RoleService;
import com.devfrank.hotelmanager.access.util.exception.RoleNotFoundException;
import com.devfrank.hotelmanager.access.util.mapper.RoleMapper;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class RoleServiceImpl implements RoleService {

    private final RoleRepository roleRepository;
    private final RoleMapper roleMapper;

    @Override
    public List<RoleDTO> findAll() {
        return roleRepository.findAllByIsActiveTrue().stream()
                .map(roleMapper::toDTO)
                .toList();
    }

    @Override
    public RoleDTO findById(UUID id) {
        return roleRepository.findByIdAndIsActiveTrue(id)
                .map(roleMapper::toDTO)
                .orElseThrow(() -> new RoleNotFoundException(id));
    }

    @Transactional
    @Override
    public RoleDTO create(SaveRoleCommand command) {
        Optional<Role> existing = roleRepository.findByNameAndIsActiveFalse(command.name());

        if (existing.isPresent()) {
            return reactivate(existing.get(), command);
        }

        Role role = roleMapper.toEntity(command);
        return roleMapper.toDTO(roleRepository.save(role));
    }

    @Transactional
    @Override
    public RoleDTO update(UUID id, SaveRoleCommand command) {
        Role role = roleRepository.findByIdAndIsActiveTrue(id)
                .orElseThrow(() -> new RoleNotFoundException(id));
        roleMapper.toEntity(role, command);
        return roleMapper.toDTO(roleRepository.save(role));
    }

    @Override
    public void delete(UUID id) {
        Role role = roleRepository.findByIdAndIsActiveTrue(id)
                .orElseThrow(() -> new RoleNotFoundException(id));
        role.setIsActive(false);
        roleRepository.save(role);
    }

    private RoleDTO reactivate(Role role, SaveRoleCommand command) {
        roleMapper.toEntity(role, command);
        role.setIsActive(Boolean.TRUE);
        return roleMapper.toDTO(roleRepository.save(role));
    }
}