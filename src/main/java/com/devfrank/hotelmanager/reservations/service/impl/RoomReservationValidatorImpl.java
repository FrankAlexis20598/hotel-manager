package com.devfrank.hotelmanager.reservations.service.impl;

import com.devfrank.hotelmanager.reservations.repository.ReservationRepository;
import com.devfrank.hotelmanager.reservations.util.enums.ReservationStatus;
import com.devfrank.hotelmanager.rooms.service.api.RoomReservationValidator;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

@Component
@RequiredArgsConstructor
public class RoomReservationValidatorImpl implements RoomReservationValidator {

    private final ReservationRepository reservationRepository;

    @Override
    public boolean hasActiveOrFutureReservations(UUID roomId) {
        return reservationRepository.existsActiveReservationsForRoomAfterDate(
                roomId,
                LocalDate.now().atStartOfDay(),
                List.of(ReservationStatus.CANCELADO, ReservationStatus.CHECK_OUT)
        );
    }
}