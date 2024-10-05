package com.jsenen.hubwarehouse.service;

import com.jsenen.hubwarehouse.domain.Component;

public interface ComponentService {
    Iterable<Component> findAll();

    Component addNewComponent(Component component);

    void deleteComponent(long id);
}
