package com.jsenen.hubwarehouse.service;

import com.jsenen.hubwarehouse.domain.ServiceOrders;

import java.util.Optional;

public interface ServiceOrderService {
    Iterable<ServiceOrders> findAll();

    ServiceOrders addNewServiceOrder(ServiceOrders serviceOrders);

    void deleteServiceOrder(long id);

    Optional<ServiceOrders> findById(long idLong);
}
