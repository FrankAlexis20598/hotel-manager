package com.devfrank.hotelmanager.reservations.dto.filter;

import com.devfrank.hotelmanager.reservations.util.enums.ReservationStatus;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.Digits;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import org.springframework.format.annotation.DateTimeFormat;

import java.math.BigDecimal;
import java.time.LocalDate;

public record ReservationCriteria(
        @Size(max = 20, message = "El número de reserva no puede superar los 20 caracteres")
        String reservationNumber,

        ReservationStatus status,

        @Pattern(regexp = "^[0-9a-fA-F]{8}-[0-9a-fA-F]{4}-[0-9a-fA-F]{4}-[0-9a-fA-F]{4}-[0-9a-fA-F]{12}$",
                message = "El ID del cliente debe ser un UUID válido")
        String customerId,

        @Size(max = 3, message = "El número de habitación no puede superar los 3 caracteres")
        String roomNumber,

        @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
        LocalDate checkInDateFrom,

        @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
        LocalDate checkInDateTo,

        @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
        LocalDate checkOutDateFrom,

        @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
        LocalDate checkOutDateTo,

        @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
        LocalDate createdAtFrom,

        @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
        LocalDate createdAtTo,

        @DecimalMin(value = "0.0", message = "El monto mínimo no puede ser negativo")
        @Digits(integer = 10, fraction = 2, message = "Formato de monto mínimo inválido")
        BigDecimal minAmount,

        @DecimalMin(value = "0.0", message = "El monto máximo no puede ser negativo")
        @Digits(integer = 10, fraction = 2, message = "Formato de monto máximo inválido")
        BigDecimal maxAmount
) {
}