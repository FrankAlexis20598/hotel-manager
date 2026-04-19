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
    public static final String ROOM_NUMBER_ALREADY_EXISTS = "El número de habitación ya se encuentra registrado y activo en el sistema.";
    public static final String ROOM_ALREADY_EXISTS_INACTIVE = "No es posible registrar esta habitación. Por favor, contacte al administrador.";
    public static final String ROOM_ALREADY_EXISTS_INACTIVE_RECOVERABLE = "La habitación ya existe pero está inactiva. Puede reactivarla para evitar duplicados.";
    public static final String ROOM_DEACTIVATE_HAS_RESERVATIONS = "No se puede desactivar la habitación porque tiene reservas activas o futuras programadas.";
    public static final String ROOM_DEACTIVATE_IS_OCCUPIED = "No se puede desactivar la habitación mientras su estado sea '%s'.";
    public static final String ROOM_ALREADY_ACTIVE = "La habitación ya se encuentra activa en el sistema.";
    public static final String ROOM_UPDATE_INACTIVE_PROHIBITED = "No es posible editar una habitación inactivo.";
    public static final String ROOM_STATUS_UPDATE_INACTIVE_PROHIBITED = "No es posible editar el estado de una habitación inactiva.";
    public static final String ROOM_MAINTENANCE_ONLY_AVAILABLE = "La habitación está en mantenimiento y solo puede marcarse como Disponible.";
    public static final String ROOM_ONLY_AVAILABLE_TO_MAINTENANCE = "La habitación debe estar Disponible para poder ponerla en mantenimiento.";
    public static final String ROOM_NOT_IN_MAINTENANCE = "La habitación no se encuentra actualmente bajo mantenimiento.";
    public static final String ROOM_ONLY_AVAILABLE_TO_CLEANING = "Solo es posible iniciar una limpieza adicional en habitaciones con estado Disponible.";
    public static final String ROOM_NOT_IN_CLEANING = "La habitación no se encuentra en estado de limpieza para ser marcada como Disponible.";

    // Payment Exceptions
    public static final String PAYMENT_COMPLETION_INVALID_STATE = "No se pudo completar el pago: Solo se permiten transiciones desde el estado %s. Estado actual: %s";
    public static final String PAYMENT_CANCELLATION_INVALID_STATE = "No se pudo anular el pago: Solo es posible anular pagos en estado %s. Estado actual: %s";
    public static final String PAYMENT_REFUND_INVALID_STATE = "No se pudo procesar el reembolso: Un pago solo puede ser reembolsado si su estado es %s. Estado actual: %s";

    // Reservation Exceptions
    public static final String RESERVATION_CONFIRMATION_INVALID_STATE = "No se pudo confirmar la reserva: Una reserva solo puede ser confirmado si su estado es %s. Estado actual: %s.";
    public static final String RESERVATION_CHECK_IN_INVALID_STATE = "No se pudo realizar el check-in de la reserva: Solo es posible realizar el check-in de reservas en estado %s. Estado actual: %s.";
    public static final String RESERVATION_CHECK_OUT_INVALID_STATE = "No se pudo realizar el check-out de la reserva: Solo es posible realizar el check-out de reservas en estado %s. Estado actual: %s.";
    public static final String RESERVATION_CANCELLATION_INVALID_STATE = "No se pudo cancelar la reserva: Una reserva solo puede ser cancelada si su estado es %s o %s. Estado actual: %s.";
    public static final String RESERVATION_ADVANCE_PAYMENT_INVALID = "No se puede confirmar la reserva: El monto pagado (%s) es inferior al monto mínimo requerido (%s) correspondiente a la primera noche.";
    public static final String RESERVATION_TOTAL_PAYMENT_INVALID = "La reserva debe contar con el pago total de la estancia. El saldo restante es: %s.";
    public static final String RESERVATION_OVERLAP = "No se puede procesar la reserva: La habitación seleccionada ya cuenta con una reserva activa para el periodo del %s al %s.";
    public static final String RESERVATION_CONFIRMATION_ROOM_UNAVAILABLE = "No se puede confirmar la reserva: La habitación ya no se encuentra disponible para las fechas seleccionadas debido a un solapamiento de último momento.";
    public static final String RESERVATION_CHECK_IN_EARLY = "No se puede realizar el Check-In: La entrada está programada para el %s. Para ingresar hoy, debe actualizar primero la reserva para incluir la noche adicional.";
    public static final String RESERVATION_CHECK_IN_EXPIRED = "No se puede realizar el Check-In: El periodo de esta reserva ya ha finalizado (Fecha de salida: %s).";
    public static final String RESERVATION_CHECK_OUT_LATE = "Check-out tardío detectado (Hora límite: %s). Se ha excedido el tiempo de gracia de %d minutos. Por favor, registre el cargo por mora y asegúrese de que el saldo esté cancelado antes de proceder.";
}
