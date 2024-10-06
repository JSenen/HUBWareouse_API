package com.jsenen.hubwarehouse.service;

import com.jsenen.hubwarehouse.domain.Component;

import java.util.Optional;

public interface ComponentService {
    Iterable<Component> findAll();

    Component addNewComponent(Component component);

    void deleteComponent(long id);


    Optional<Component> findByPartNumber(String partNumber);
}
