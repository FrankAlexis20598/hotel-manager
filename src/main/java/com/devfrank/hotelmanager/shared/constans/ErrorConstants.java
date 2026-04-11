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

    // Room Exceptions
    public static final String ROOM_ALREADY_EXISTS_INACTIVE = "La habitación ya existe, pero está inactivo";
    public static final String ROOM_FORBIDDEN_INACTIVE_VIEW = "No tiene permiso para ver habitaciones inactivos";
    public static final String ROOM_UPDATE_INACTIVE_PROHIBITED = "No es posible editar una habitación inactivo";
    public static final String ROOM_STATUS_UPDATE_INACTIVE_PROHIBITED = "No es posible editar el estado de una habitación inactivo";
    public static final String ROOM_MAINTENANCE_ONLY_AVAILABLE = "La habitación está en mantenimiento y solo puede marcarse como disponible.";

    // Payment Exceptions
    public static final String PAYMENT_COMPLETION_INVALID_STATE = "No se pudo completar el pago: Solo se permiten transiciones desde el estado %s. Estado actual: %s";
    public static final String PAYMENT_CANCELLATION_INVALID_STATE = "No se pudo anular el pago: Solo es posible anular pagos en estado %s o %s. Estado actual: %s";
    public static final String PAYMENT_REFUND_INVALID_STATE = "No se pudo procesar el reembolso: Un pago solo puede ser reembolsado si su estado es %s. Estado actual: %s";
}
