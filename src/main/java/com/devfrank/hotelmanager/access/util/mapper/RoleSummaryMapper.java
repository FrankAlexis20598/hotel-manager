package com.devfrank.hotelmanager.access.util.mapper;

import com.devfrank.hotelmanager.access.dto.RoleSummaryDTO;
import com.devfrank.hotelmanager.access.entity.Role;
import com.devfrank.hotelmanager.shared.base.BaseMapper;
import org.springframework.stereotype.Component;

import java.util.UUID;

@Component
public class RoleSummaryMapper implements BaseMapper<Role, RoleSummaryDTO> {

    @Override
    public RoleSummaryDTO toDTO(Role entity) {
        return new RoleSummaryDTO(
                entity.getId(),
                entity.getName()
        );
    }

    public Role fromUUID(UUID id) {
        return new Role(id);
    }
}