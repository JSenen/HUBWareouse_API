package com.jsenen.hubwarehouse.repository;

import com.jsenen.hubwarehouse.domain.Component;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ComponentRepository extends CrudRepository<Component,Long> {

    List<Component> findAll();

    Optional<Component> findByPartNumberComponent(String partNumber);

    // Método para buscar coincidencias parciales tanto en partNumberComponent como en descriptionComponent
    List<Component> findByPartNumberComponentContainingOrDescriptionComponentContaining(String partNumberComponent, String descriptionComponent);

}
