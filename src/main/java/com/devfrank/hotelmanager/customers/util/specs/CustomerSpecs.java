package com.devfrank.hotelmanager.customers.util.specs;

import com.devfrank.hotelmanager.customers.dto.filter.CustomerCriteria;
import com.devfrank.hotelmanager.customers.entity.Customer;
import jakarta.persistence.criteria.Predicate;
import org.springframework.data.jpa.domain.Specification;

import java.util.ArrayList;

public class CustomerSpecs {
    public static Specification<Customer> filter(CustomerCriteria criteria) {
        return (root, query, cb) -> {
            var predicates = new ArrayList<Predicate>();

            if (criteria.fullName() != null) {
                predicates.add(cb.like(cb.lower(root.get("names")), "%" + criteria.fullName().toLowerCase() + "%"));
                predicates.add(cb.like(cb.lower(root.get("surnames")), "%" + criteria.fullName().toLowerCase() + "%"));
            }

            if (criteria.email() != null) {
                predicates.add(cb.like(cb.lower(root.get("email")), "%" + criteria.email().toLowerCase() + "%"));
            }

            if (criteria.documentNumber() != null) {
                predicates.add(cb.equal(root.get("documentNumber"), criteria.documentNumber()));
            }

            return cb.and(predicates.toArray(new Predicate[0]));
        };
    }
}