package com.devfrank.hotelmanager.rooms.dto.filter;

import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class RoomCriteria {

    @Pattern(regexp = "^(true|all)$", message = "El valor del filtro is_active debe ser true o all")
    private String isActive;

    private String number;

    @Pattern(regexp = "^(disponible|ocupada|mantenimiento)$", message = "El valor del filtro status debe ser disponible, ocupada o mantenimiento")
    private String status;

    @Pattern(regexp = "^(individual|doble|suite)$", message = "El valor del filtro type debe ser individual, doble o suite")
    private String type;
}