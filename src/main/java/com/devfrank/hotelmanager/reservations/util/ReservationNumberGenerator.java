package com.devfrank.hotelmanager.reservations.util;

import com.devfrank.hotelmanager.reservations.repository.ReservationRepository;
import com.devfrank.hotelmanager.shared.constans.UtilConstants;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
@RequiredArgsConstructor
public class ReservationNumberGenerator {

    private final ReservationRepository reservationRepository;

    public synchronized String generateNext(int year) {
        String yearStr = String.valueOf(year);
        Optional<String> lastNumberOpt = reservationRepository.findLastReservationNumberByYear(yearStr);

        int nextNumber = 1;
        if (lastNumberOpt.isPresent()) {
            String last = lastNumberOpt.get();
            nextNumber = Integer.parseInt(last.substring(last.length() - 5)) + 1;
        }

        return String.format(UtilConstants.RESERVATION_NUMBER_FORMAT,
                UtilConstants.RESERVATION_NUMBER_PREFIX, yearStr, nextNumber);
    }
}