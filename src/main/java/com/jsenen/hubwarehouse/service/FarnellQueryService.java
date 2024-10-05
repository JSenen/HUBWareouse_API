package com.jsenen.hubwarehouse.service;

import com.jsenen.hubwarehouse.domain.FarnellComponent;

public interface FarnellQueryService {
    Iterable<FarnellComponent> findAll();

    FarnellComponent findOne(String partnumber);
}
