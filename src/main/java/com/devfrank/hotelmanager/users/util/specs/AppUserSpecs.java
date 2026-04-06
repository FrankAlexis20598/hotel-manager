package com.devfrank.hotelmanager.users.util.specs;

import com.devfrank.hotelmanager.access.entity.Role;
import com.devfrank.hotelmanager.users.dto.filter.AppUserCriteria;
import com.devfrank.hotelmanager.users.entity.AppUser;
import jakarta.persistence.criteria.Join;
import jakarta.persistence.criteria.Predicate;
import org.springframework.data.jpa.domain.Specification;

import java.util.ArrayList;
import java.util.List;

public class AppUserSpecs {
    public static Specification<AppUser> filter(AppUserCriteria criteria) {
        return (root, query, cb) -> {
            List<Predicate> predicates = new ArrayList<>();

            if (criteria.getEmail() != null && !criteria.getEmail().isBlank()) {
                String likeSearch = "%" + criteria.getEmail().toLowerCase() + "%";
                predicates.add(cb.like(cb.lower(root.get("email")), likeSearch));
            }

            if (criteria.getRole() != null && !criteria.getRole().isBlank()) {
                Join<AppUser, Role> roleJoin = root.join("role");
                predicates.add(cb.equal(roleJoin.get("name"), criteria.getRole()));
            }

            return cb.and(predicates.toArray(new Predicate[0]));
        };
    }
}