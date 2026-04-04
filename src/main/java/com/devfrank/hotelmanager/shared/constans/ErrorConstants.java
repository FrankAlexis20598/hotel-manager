package com.devfrank.hotelmanager.shared.constans;

public class ErrorConstants {

    private ErrorConstants() {
        throw new IllegalStateException("Utility class");
    }

    // Customer Exceptions
    public static final String CUSTOMER_ALREADY_EXISTS_INACTIVE = "El cliente ya existe, pero está dado de baja";
    public static final String CUSTOMER_FORBIDDEN_INACTIVE_VIEW = "No tiene permiso para ver clientes inactivos";
    public static final String CUSTOMER_UPDATE_INACTIVE_PROHIBITED = "No es posible editar un cliente inactivo";
}
