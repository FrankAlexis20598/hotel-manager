package com.devfrank.hotelmanager.reservations.util.mapper;

import com.devfrank.hotelmanager.customers.entity.Customer;
import com.devfrank.hotelmanager.customers.util.mapper.CustomerMapper;
import com.devfrank.hotelmanager.payments.entity.Payment;
import com.devfrank.hotelmanager.payments.util.enums.PaymentStatus;
import com.devfrank.hotelmanager.payments.util.mapper.PaymentMapper;
import com.devfrank.hotelmanager.reservations.dto.ReservationDTO;
import com.devfrank.hotelmanager.reservations.dto.ReservationDetailDTO;
import com.devfrank.hotelmanager.reservations.dto.request.CreateReservationRequest;
import com.devfrank.hotelmanager.reservations.dto.request.UpdateReservationRequest;
import com.devfrank.hotelmanager.reservations.entity.Reservation;
import com.devfrank.hotelmanager.reservations.util.dto.ReservationExtraInfoDTO;
import com.devfrank.hotelmanager.reservations.util.enums.ReservationStatus;
import com.devfrank.hotelmanager.rooms.util.mapper.RoomMapper;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

import java.math.BigDecimal;

@Mapper(componentModel = "spring", uses = {CustomerMapper.class, RoomMapper.class, PaymentMapper.class})
public interface ReservationMapper {

    @Mapping(target = "customerName", source = "customer")
    @Mapping(target = "roomNumber", source = "room.number")
    @Mapping(target = "roomType", source = "room.type")
    ReservationDTO toDTO(Reservation reservation);

    @Mapping(target = "payments", qualifiedByName = "toDTO")
    @Mapping(target = "customer", qualifiedByName = "toSummaryDTO")
    @Mapping(target = "room", qualifiedByName = "toSummaryDTO")
    @Mapping(target = "remainingBalance", expression = "java(calculateRemainingBalance(reservation))")
    ReservationDetailDTO toDetailDTO(Reservation reservation);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "status", expression = "java(defaultStatusFromString())")
    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "createdBy", ignore = true)
    @Mapping(target = "updatedAt", ignore = true)
    @Mapping(target = "updatedBy", ignore = true)
    @Mapping(target = "totalAmount", ignore = true)
    @Mapping(target = "refundableAmount", ignore = true)
    @Mapping(target = "cancellationReason", ignore = true)
    @Mapping(target = "cancelledAt", ignore = true)
    @Mapping(target = "customer", source = "request.customerId", qualifiedByName = "idToEntity")
    @Mapping(target = "room", source = "request.roomId", qualifiedByName = "idToEntity")
    @Mapping(target = "payments", ignore = true)
    Reservation toEntity(CreateReservationRequest request, ReservationExtraInfoDTO extraInfo);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "reservationNumber", ignore = true)
    @Mapping(target = "status", ignore = true)
    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "createdBy", ignore = true)
    @Mapping(target = "updatedAt", ignore = true)
    @Mapping(target = "updatedBy", ignore = true)
    @Mapping(target = "totalAmount", ignore = true)
    @Mapping(target = "refundableAmount", ignore = true)
    @Mapping(target = "cancellationReason", ignore = true)
    @Mapping(target = "cancelledAt", ignore = true)
    @Mapping(target = "customer", ignore = true)
    @Mapping(target = "room", source = "request.roomId", qualifiedByName = "idToEntity")
    @Mapping(target = "payments", ignore = true)
    void updateEntity(UpdateReservationRequest request, ReservationExtraInfoDTO extraInfo, @MappingTarget Reservation reservation);

    default String toCustomerName(Customer customer) {
        return customer.getNames() + " " + customer.getSurnames();
    }

    default BigDecimal calculateRemainingBalance(Reservation reservation) {
        BigDecimal paidBalance = reservation.getPayments().stream()
                .filter(p -> p.getStatus() == PaymentStatus.PAGADO)
                .map(Payment::getAmount)
                .reduce(BigDecimal.ZERO, BigDecimal::add);
        return reservation.getTotalAmount().subtract(paidBalance);
    }

    default ReservationStatus defaultStatusFromString() {
        return ReservationStatus.PENDIENTE;
    }
}