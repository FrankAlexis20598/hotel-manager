package com.devfrank.hotelmanager.access.dto.filter;

import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class RoleCriteria {
    @Pattern(regexp = "^(true|all)$", message = "El filtro is_active debe ser true o all")
    private String isActive;
}