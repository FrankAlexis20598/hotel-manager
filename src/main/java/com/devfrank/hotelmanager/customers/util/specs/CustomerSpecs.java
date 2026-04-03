package com.devfrank.hotelmanager.customers.util.specs;

import com.devfrank.hotelmanager.customers.dto.filter.CustomerCriteria;
import com.devfrank.hotelmanager.customers.entity.Customer;
import jakarta.persistence.criteria.Expression;
import jakarta.persistence.criteria.Predicate;
import org.springframework.data.jpa.domain.Specification;

import java.util.ArrayList;
import java.util.List;

public class CustomerSpecs {
    public static Specification<Customer> filter(CustomerCriteria criteria) {
        return (root, query, cb) -> {
            List<Predicate> predicates = new ArrayList<>();

            if (criteria.getIsActive() != null && !criteria.getIsActive().equals("all")) {
                predicates.add(cb.equal(root.get("isActive"), Boolean.parseBoolean(criteria.getIsActive())));
            }

            if (criteria.getSearch() != null && !criteria.getSearch().isBlank()) {
                String likeSearch = "%" + criteria.getSearch().toLowerCase() + "%";
                Expression<String> fullName = cb.concat(
                        cb.concat(root.get("names"), " "),
                        root.get("surnames")
                );
                Predicate searchPredicate = cb.or(
                        cb.like(cb.lower(fullName), likeSearch),
                        cb.like(cb.lower(root.get("email")), likeSearch),
                        cb.like(cb.lower(root.get("documentNumber")), likeSearch)
                );
                predicates.add(searchPredicate);
            }

            return cb.and(predicates.toArray(new Predicate[0]));
        };
    }
}