package com.devfrank.hotelmanager.reservations.dto.response;

import com.devfrank.hotelmanager.customers.dto.response.CustomerResponse;
import com.devfrank.hotelmanager.payments.dto.response.PaymentResponse;
import com.devfrank.hotelmanager.reservations.dto.ReservationDetailDTO;
import com.devfrank.hotelmanager.rooms.dto.response.RoomResponse;
import com.fasterxml.jackson.annotation.JsonInclude;

import java.math.BigDecimal;
import java.util.List;

@JsonInclude(JsonInclude.Include.NON_NULL)
public record ReservationDetailResponse(
        String id,
        String reservationNumber,
        CustomerResponse customer,
        RoomResponse room,
        String checkInDate,
        String checkOutDate,
        String status,
        BigDecimal totalAmount,
        List<PaymentResponse> payments,
        BigDecimal remainingBalance,
        String createdBy,
        String createdAt
) {
    public static ReservationDetailResponse fromDTO(ReservationDetailDTO dto) {
        return new ReservationDetailResponse(
                dto.id().toString(),
                dto.reservationNumber(),
                getCustomer(dto),
                getRoom(dto),
                dto.checkInDate().toString(),
                dto.checkOutDate().toString(),
                dto.status().name(),
                dto.totalAmount(),
                getPayments(dto),
                dto.remainingBalance(),
                dto.createdBy(),
                dto.createdAt().toString()
        );
    }

    private static CustomerResponse getCustomer(ReservationDetailDTO dto) {
        return new CustomerResponse(
                dto.customer().id().toString(),
                dto.customer().names(),
                dto.customer().surnames(),
                dto.customer().email(),
                dto.customer().phone(),
                dto.customer().documentType().getValue(),
                dto.customer().documentNumber(),
                null
        );
    }

    private static RoomResponse getRoom(ReservationDetailDTO dto) {
        return new RoomResponse(
                dto.room().id().toString(),
                dto.room().number(),
                dto.room().type().name(),
                dto.room().price(),
                null
        );
    }

    private static List<PaymentResponse> getPayments(ReservationDetailDTO dto) {
        return dto.payments().stream()
                .map(p -> new PaymentResponse(
                        p.id().toString(),
                        p.amount(),
                        p.paymentDate().toString(),
                        p.paymentMethod().name(),
                        p.status().name()
                ))
                .toList();
    }
}