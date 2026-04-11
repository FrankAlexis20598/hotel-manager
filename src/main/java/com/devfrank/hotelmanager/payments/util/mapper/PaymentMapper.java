package com.devfrank.hotelmanager.payments.util.mapper;

import com.devfrank.hotelmanager.payments.dto.PaymentDTO;
import com.devfrank.hotelmanager.payments.dto.request.CreatePaymentRequest;
import com.devfrank.hotelmanager.payments.entity.Payment;
import com.devfrank.hotelmanager.payments.util.enums.PaymentMethod;
import com.devfrank.hotelmanager.payments.util.enums.PaymentStatus;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;

import java.time.LocalDate;
import java.util.UUID;

@Mapper(componentModel = "spring")
public interface PaymentMapper {
    PaymentDTO toDTO(Payment payment);

    @Mapping(target = "id", expression = "java(defaultValueForId())")
    @Mapping(target = "paymentDate", qualifiedByName = "defaultValueForPaymentDate")
    @Mapping(target = "paymentMethod", qualifiedByName = "stringToPaymentMethod")
    @Mapping(target = "status", expression = "java(defaultValueForPaymentStatus())")
    @Mapping(target = "statusReason", ignore = true)
    @Mapping(target = "observations", ignore = true)
    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "createdBy", ignore = true)
    @Mapping(target = "updatedAt", ignore = true)
    @Mapping(target = "updatedBy", ignore = true)
    Payment toEntity(CreatePaymentRequest request);

    @Named("defaultValueForPaymentDate")
    default LocalDate defaultValueForPaymentDate(LocalDate date) {
        return date == null ? LocalDate.now() : date;
    }

    @Named("defaultValueForPaymentStatus")
    default PaymentStatus defaultValueForPaymentStatus() {
        return PaymentStatus.PENDIENTE;
    }

    @Named("defaultValueForId")
    default UUID defaultValueForId() {
        return UUID.randomUUID();
    }

    @Named("stringToPaymentMethod")
    default PaymentMethod stringToPaymentMethod(String value) {
        return PaymentMethod.valueOf(value.toUpperCase());
    }
}