package com.devfrank.hotelmanager.payments.util.specs;

import com.devfrank.hotelmanager.payments.dto.filter.PaymentCriteria;
import com.devfrank.hotelmanager.payments.entity.Payment;
import com.devfrank.hotelmanager.payments.util.enums.PaymentMethod;
import com.devfrank.hotelmanager.payments.util.enums.PaymentStatus;
import jakarta.persistence.criteria.Predicate;
import org.springframework.data.jpa.domain.Specification;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class PaymentSpecs {
    public static Specification<Payment> filter(PaymentCriteria criteria) {
        return (root, query, cb) -> {
            List<Predicate> predicates = new ArrayList<>();

            if (criteria.getStatus() != null && !criteria.getStatus().isBlank()) {
                predicates.add(cb.equal(root.get("status"), PaymentStatus.valueOf(criteria.getStatus().toUpperCase())));
            }

            if (criteria.getPaymentMethod() != null && !criteria.getPaymentMethod().isBlank()) {
                predicates.add(cb.equal(root.get("paymentMethod"), PaymentMethod.valueOf(criteria.getPaymentMethod().toUpperCase())));
            }

            if (criteria.getDateFrom() != null && !criteria.getDateFrom().isBlank()) {
                LocalDate dateFrom = LocalDate.parse(criteria.getDateFrom());
                LocalDate dateTo = (criteria.getDateTo() != null && !criteria.getDateTo().isBlank())
                        ? LocalDate.parse(criteria.getDateTo())
                        : LocalDate.now();
                predicates.add(cb.between(root.get("paymentDate"), dateFrom, dateTo));
            } else if (criteria.getDateTo() != null && !criteria.getDateTo().isBlank()) {
                LocalDate dateTo = LocalDate.parse(criteria.getDateTo());
                predicates.add(cb.lessThanOrEqualTo(root.get("paymentDate"), dateTo));
            }

            return cb.and(predicates.toArray(new Predicate[0]));
        };
    }
}