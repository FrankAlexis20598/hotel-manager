package com.devfrank.hotelmanager.security.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;

public record LoginRequest(
        @Email(message = "El campo email debe ser una dirección de correo electrónico con formato correcto")
        @NotBlank(message = "El campo email no debe estar vacío")
        String email,
        @NotBlank(message = "El campo password no debe estar vacío")
        String password
) {
}