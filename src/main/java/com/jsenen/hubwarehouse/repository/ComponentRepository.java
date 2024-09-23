package com.jsenen.hubwarehouse.repository;

import com.jsenen.hubwarehouse.domain.Component;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ComponentRepository extends CrudRepository<Component,Long> {

    List<Component> findAll();
}
