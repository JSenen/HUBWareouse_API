package com.jsenen.hubwarehouse.service;

import com.jsenen.hubwarehouse.domain.Component;
import com.jsenen.hubwarehouse.domain.FarnellComponent;
import com.jsenen.hubwarehouse.exception.EntityNotFound;

import java.util.Optional;

public interface ComponentService {
    Iterable<Component> findAll();

    Component addNewComponent(Component component);

    void deleteComponent(long id);


    Optional<Component> findByPartNumber(String partNumber);

    FarnellComponent searchPNumberFarnell(String productNumber);

    Component addNewComponentFromWeb(Component component);

    Component updateComponent(long id, Component component) throws EntityNotFound;

    Optional<Component> findById(long idComponent);
}
