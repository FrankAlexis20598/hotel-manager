package com.devfrank.hotelmanager.reservations.repository;

import com.devfrank.hotelmanager.reservations.entity.Reservation;
import com.devfrank.hotelmanager.reservations.util.enums.ReservationStatus;
import com.devfrank.hotelmanager.shared.base.repository.SearchableRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface ReservationRepository extends SearchableRepository<Reservation, UUID> {

    @Query("""
            SELECT MAX(r.reservationNumber) FROM Reservation r
            WHERE r.reservationNumber
            LIKE :yearPrefix
            """)
    Optional<String> findLastReservationNumberByYear(@Param("yearPrefix") String yearPrefix);

    @Query("""
            SELECT COUNT(r) > 0 FROM Reservation r
                        WHERE r.room.id = :roomId
                        AND (:excludeReservationId IS NULL OR r.id <> :excludeReservationId)
                        AND r.status IN(ReservationStatus.CONFIRMADO, ReservationStatus.CHECK_IN)
                        AND (:checkInDate < r.checkOutDate AND :checkOutDate > r.checkInDate)
            """)
    boolean existsOverlapForRoomExcludingReservation(@Param("roomId") UUID roomId,
                                                     @Param("excludeReservationId") UUID excludeId,
                                                     @Param("checkInDate") LocalDateTime checkInDate,
                                                     @Param("checkOutDate") LocalDateTime checkOutDate);

    @Query("""
            SELECT CASE WHEN EXISTS (
            SELECT 1 FROM  Reservation r
            WHERE r.room.id = :roomId
            AND r.status NOT IN (:excludedStatuses)
            AND r.checkOutDate >= :currentDateStart
            ) THEN TRUE ELSE FALSE END
            """)
    boolean existsActiveReservationsForRoomAfterDate(@Param("roomId") UUID roomId,
                                                     @Param("currentDateStart") LocalDateTime currentDateStart,
                                                     @Param("excludedStatuses") List<ReservationStatus> excludedStatuses);
}