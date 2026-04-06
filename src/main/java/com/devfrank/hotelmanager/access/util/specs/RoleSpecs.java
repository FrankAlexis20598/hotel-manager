package com.devfrank.hotelmanager.access.util.specs;

import com.devfrank.hotelmanager.access.dto.filter.RoleCriteria;
import com.devfrank.hotelmanager.access.entity.Role;
import com.devfrank.hotelmanager.shared.constans.UtilConstants;
import jakarta.persistence.criteria.Predicate;
import org.springframework.data.jpa.domain.Specification;

import java.util.ArrayList;
import java.util.List;

public class RoleSpecs {
    public static Specification<Role> filter(RoleCriteria criteria) {
        return (root, query, cb) -> {
            List<Predicate> predicates = new ArrayList<>();

            if (criteria.getIsActive() != null
                    && !criteria.getIsActive().equals(UtilConstants.IS_ACTIVE_FILTER_ALL)) {
                predicates.add(cb.equal(root.get("isActive"), Boolean.parseBoolean(criteria.getIsActive())));
            }

            return cb.and(predicates.toArray(new Predicate[0]));
        };
    }
}