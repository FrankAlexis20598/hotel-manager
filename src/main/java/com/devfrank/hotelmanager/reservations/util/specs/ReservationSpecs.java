package com.devfrank.hotelmanager.reservations.util.specs;

import com.devfrank.hotelmanager.reservations.dto.filter.ReservationCriteria;
import com.devfrank.hotelmanager.reservations.entity.Reservation;
import jakarta.persistence.criteria.Predicate;
import org.springframework.data.jpa.domain.Specification;

import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class ReservationSpecs {
    public static Specification<Reservation> filter(ReservationCriteria criteria) {
        return (root, query, cb) -> {
            List<Predicate> predicates = new ArrayList<>();

            if (criteria.reservationNumber() != null && !criteria.reservationNumber().isBlank()) {
                String likeSearch = "%" + criteria.reservationNumber().toLowerCase() + "%";
                predicates.add(cb.like(cb.lower(root.get("reservationNumber")), likeSearch));
            }

            if (criteria.status() != null) {
                predicates.add(cb.equal(root.get("status"), criteria.status()));
            }

            if (criteria.customerId() != null && !criteria.customerId().isBlank()) {
                predicates.add(cb.equal(root.get("customer").get("id"), UUID.fromString(criteria.customerId())));
            }

            if (criteria.roomNumber() != null && !criteria.roomNumber().isBlank()) {
                String likeSearch = "%" + criteria.roomNumber().toLowerCase() + "%";
                predicates.add(cb.like(cb.lower(root.get("room").get("number")), likeSearch));
            }

            if (criteria.checkInDateFrom() != null) {
                predicates.add(cb.greaterThanOrEqualTo(root.get("checkInDate"), criteria.checkInDateFrom()));
            }

            if (criteria.checkInDateTo() != null) {
                predicates.add(cb.lessThanOrEqualTo(root.get("checkInDate"), criteria.checkInDateTo()));
            }

            if (criteria.checkOutDateFrom() != null) {
                predicates.add(cb.greaterThanOrEqualTo(root.get("checkOutDate"), criteria.checkOutDateFrom()));
            }

            if (criteria.checkOutDateTo() != null) {
                predicates.add(cb.lessThanOrEqualTo(root.get("checkOutDate"), criteria.checkOutDateTo()));
            }

            if (criteria.createdAtFrom() != null) {
                predicates.add(cb.greaterThanOrEqualTo(root.get("createdAt"), criteria.createdAtFrom().atStartOfDay()));
            }

            if (criteria.createdAtTo() != null) {
                predicates.add(cb.lessThanOrEqualTo(root.get("createdAt"), criteria.createdAtTo().atTime(LocalTime.MAX)));
            }

            if (criteria.minAmount() != null) {
                predicates.add(cb.greaterThanOrEqualTo(root.get("totalAmount"), criteria.minAmount()));
            }

            if (criteria.maxAmount() != null) {
                predicates.add(cb.lessThanOrEqualTo(root.get("totalAmount"), criteria.maxAmount()));
            }

            return cb.and(predicates.toArray(new Predicate[0]));
        };
    }
}