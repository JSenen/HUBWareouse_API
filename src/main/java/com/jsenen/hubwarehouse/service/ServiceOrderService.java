package com.jsenen.hubwarehouse.service;

import com.jsenen.hubwarehouse.domain.ServiceOrders;

public interface ServiceOrderService {
    Iterable<ServiceOrders> findAll();

    ServiceOrders addNewServiceOrder(ServiceOrders serviceOrders);
}
