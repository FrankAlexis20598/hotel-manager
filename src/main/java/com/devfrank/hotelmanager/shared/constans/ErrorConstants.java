package com.devfrank.hotelmanager.shared.constans;

public class ErrorConstants {

    private ErrorConstants() {
        throw new IllegalStateException("Utility class");
    }

    // Customer Exceptions
    public static final String CUSTOMER_ALREADY_EXISTS_INACTIVE = "El cliente ya existe, pero está dado de baja";
    public static final String CUSTOMER_FORBIDDEN_INACTIVE_VIEW = "No tiene permiso para ver clientes inactivos";
    public static final String CUSTOMER_UPDATE_INACTIVE_PROHIBITED = "No es posible editar un cliente inactivo";

    // Auth Exceptions
    public static final String AUTH_INVALID_REFRESH_TOKEN = "Refresh token no válido o no encontrado";
    public static final String AUTH_EXPIRED_REFRESH_TOKEN = "Refresh token expirado. Inicie sesión de nuevo";
    public static final String AUTH_INVALID_PASSWORD_TOKEN = "Token de recuperación de contraseña no válido";
    public static final String AUTH_EXPIRED_PASSWORD_TOKEN = "El token de recuperación de contraseña ha expirado";
}
