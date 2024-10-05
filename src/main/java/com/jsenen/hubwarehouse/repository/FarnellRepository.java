package com.jsenen.hubwarehouse.repository;

import com.jsenen.hubwarehouse.domain.Component;
import com.jsenen.hubwarehouse.domain.FarnellComponent;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface FarnellRepository extends CrudRepository<FarnellComponent,Long> {
}
