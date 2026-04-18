package com.devfrank.hotelmanager.shared.util;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public final class PaginationUtils {

    public static final int DEFAULT_PAGE_NUMBER = 0;
    public static final int DEFAULT_PAGE_SIZE = 10;

    public static Pageable ensureSort(Pageable pageable, String defaultSortField, Sort.Direction direction) {
        if (pageable == null) {
            return PageRequest.of(
                    DEFAULT_PAGE_NUMBER,
                    DEFAULT_PAGE_SIZE,
                    Sort.by(direction, defaultSortField)
            );
        }

        if (pageable.isPaged() && pageable.getSort().isUnsorted()) {
            return PageRequest.of(
                    pageable.getPageNumber(),
                    pageable.getPageSize(),
                    Sort.by(direction, defaultSortField)
            );
        }
        return pageable;
    }

}