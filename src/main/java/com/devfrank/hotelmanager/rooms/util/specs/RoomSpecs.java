package com.devfrank.hotelmanager.rooms.util.specs;

import com.devfrank.hotelmanager.rooms.dto.filter.RoomCriteria;
import com.devfrank.hotelmanager.rooms.entity.Room;
import com.devfrank.hotelmanager.shared.constans.UtilConstants;
import jakarta.persistence.criteria.Predicate;
import org.springframework.data.jpa.domain.Specification;

import java.util.ArrayList;
import java.util.List;

public class RoomSpecs {
    public static Specification<Room> filter(RoomCriteria criteria) {
        return (root, query, cb) -> {
            List<Predicate> predicates = new ArrayList<>();

            if (criteria.getIsActive() != null
                    && !criteria.getIsActive().equals(UtilConstants.IS_ACTIVE_FILTER_ALL)) {
                predicates.add(cb.equal(root.get("isActive"), Boolean.parseBoolean(criteria.getIsActive())));
            }

            if (criteria.getNumber() != null && !criteria.getNumber().isBlank()) {
                String likeSearch = "%" + criteria.getNumber().toLowerCase() + "%";
                predicates.add(cb.like(root.get("number"), likeSearch));
            }

            if (criteria.getStatus() != null && !criteria.getStatus().isBlank()) {
                String likeSearch = "%" + criteria.getStatus().toLowerCase() + "%";
                predicates.add(cb.like(root.get("status"), likeSearch));
            }

            if (criteria.getType() != null && !criteria.getType().isBlank()) {
                String likeSearch = "%" + criteria.getType().toLowerCase() + "%";
                predicates.add(cb.like(root.get("type"), likeSearch));
            }

            return cb.and(predicates.toArray(new Predicate[0]));
        };
    }
}