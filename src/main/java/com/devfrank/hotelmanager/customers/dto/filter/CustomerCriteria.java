package com.devfrank.hotelmanager.customers.dto.filter;

import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CustomerCriteria {

    @Pattern(regexp = "^(true|all)$", message = "El valor debe ser true o all")
    private String isActive;

    private String search;
}