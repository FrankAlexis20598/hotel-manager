package com.devfrank.hotelmanager.shared.constans;

public class ValidationConstants {

    private ValidationConstants() {
        throw new IllegalStateException("Utility class");
    }

    // Customer Validations
    public static final String CUSTOMER_NAMES_NOT_BLANK = "Los nombres no pueden estar vacíos";
    public static final String CUSTOMER_NAMES_SIZE = "Los nombres deben tener entre 2 y 150 caracteres";
    public static final String CUSTOMER_SURNAMES_NOT_BLANK = "Los apellidos no pueden estar vacíos";
    public static final String CUSTOMER_SURNAMES_SIZE = "Los apellidos deben tener entre 2 y 150 caracteres";
    public static final String CUSTOMER_EMAIL_NOT_BLANK = "El correo electrónico no puede estar vacío";
    public static final String CUSTOMER_EMAIL_FORMAT = "El correo electrónico debe tener un formato válido";
    public static final String CUSTOMER_EMAIL_SIZE = "El correo electrónico debe tener entre 5 y 150 caracteres";
    public static final String CUSTOMER_PHONE_SIZE = "El teléfono debe tener entre 9 y 15 caracteres";
    public static final String CUSTOMER_PHONE_PATTERN = "El teléfono debe ser un número móvil válido de Perú (9 dígitos y empezar con 9)";
    public static final String CUSTOMER_DOCUMENT_TYPE_NOT_BLANK = "El tipo de documento es obligatorio";
    public static final String CUSTOMER_DOCUMENT_TYPE_SIZE = "El tipo de documento debe tener exactamente 2 caracteres";
    public static final String CUSTOMER_DOCUMENT_TYPE_PATTERN = "El tipo de documento solo puede ser '01' (DNI) o '04' (Carnet de Extranjería)";
    public static final String CUSTOMER_DOCUMENT_NUMBER_NOT_BLANK = "El número de documento es obligatorio";
    public static final String CUSTOMER_DOCUMENT_NUMBER_SIZE = "El número de documento debe tener entre 8 y 20 caracteres";
    public static final String CUSTOMER_DOCUMENT_NUMBER_PATTERN = "El número de documento debe ser de 8 dígitos (DNI) o hasta 12 caracteres alfanuméricos (CE)";
    public static final String CUSTOMER_DOCUMENT_INVALID = "El número de documento no es válido para el tipo seleccionado";

    // Role Validations
    public static final String ROLE_NAME_NOT_BLANK = "El nombre del rol no puede estar vacío";
    public static final String ROLE_NAME_SIZE = "El nombre del rol debe tener como máximo 50 caracteres";
    public static final String ROLE_DESCRIPTION_NOT_BLANK = "La descripción del rol no puede estar vacía";
    public static final String ROLE_DESCRIPTION_SIZE = "La descripción del rol debe tener como máximo 255 caracteres";
    public static final String ROLE_PERMISSIONS_NOT_EMPTY = "La lista de permisos no puede estar vacía";
    public static final String ROLE_PERMISSIONS_ID_NOT_BLANK = "Cada ID de permiso no puede estar vacío";
    public static final String ROLE_PERMISSIONS_ID_SIZE = "Cada ID de permiso debe tener exactamente 36 caracteres";
    public static final String ROLE_PERMISSIONS_ID_UUID_FORMAT = "Cada ID de permiso debe tener un formato UUID válido";

    // Room Validations
    public static final String ROOM_NUMBER_PATTERN = "El número de habitación debe tener exactamente 3 dígitos";
    public static final String ROOM_TYPE_INVALID = "El tipo de habitación no es válido. Los tipos permitidos son: INDIVIDUAL, DOBLE, SUITE";
    public static final String ROOM_PRICE_MIN = "El precio debe ser mayor que cero";
    public static final String ROOM_STATUS_NOT_BLANK = "El estado de la habitación no puede estar vacío";
    public static final String ROOM_STATUS_INVALID = "El estado de la habitación no es válido. Los estados permitidos son: DISPONIBLE, OCUPADA, MANTENIMIENTO";

    // Payment Validations
    public static final String PAYMENT_STATUS_INVALID = "El estado de pago no es válido. Los estados permitidos son: PENDIENTE, PAGADO, ANULADO, REEMBOLSADO";
    public static final String PAYMENT_METHOD_INVALID = "El método de pago no es válido. Los métodos permitidos son: EFECTIVO, TARJETA_DEBITO, TARJETA_CREDITO, TRANSFERENCIA, BILLETERA_DIGITAL";
    public static final String PAYMENT_DATE_FORMAT_INVALID = "El formato de fecha debe ser YYYY-MM-DD";

    // Generic Validations
    public static final String FIELD_NOT_BLANK_IF_PRESENT = "Si se proporciona, el campo no puede estar vacío";
}
