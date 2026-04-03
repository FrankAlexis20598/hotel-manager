package com.devfrank.hotelmanager.shared.base;

import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

public interface SearchableRepository<E, ID> extends BaseRepository<E, ID>, JpaSpecificationExecutor<E> {
}