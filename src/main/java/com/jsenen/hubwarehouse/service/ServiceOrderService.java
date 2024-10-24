package com.jsenen.hubwarehouse.service;

import com.jsenen.hubwarehouse.domain.ServiceOrders;
import com.jsenen.hubwarehouse.exception.EntityNotFound;

import java.util.Optional;

public interface ServiceOrderService {
    Iterable<ServiceOrders> findAll();

    ServiceOrders addNewServiceOrder(ServiceOrders serviceOrders);

    void deleteServiceOrder(long id);

    Optional<ServiceOrders> findById(long idLong);

    ServiceOrders updateServiceOrder(long id, ServiceOrders serviceOrders) throws EntityNotFound;
}
