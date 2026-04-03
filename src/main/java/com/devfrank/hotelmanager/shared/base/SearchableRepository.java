package com.devfrank.hotelmanager.shared.base;

import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.NoRepositoryBean;

@NoRepositoryBean
public interface SearchableRepository<E, ID> extends BaseRepository<E, ID>, JpaSpecificationExecutor<E> {
}