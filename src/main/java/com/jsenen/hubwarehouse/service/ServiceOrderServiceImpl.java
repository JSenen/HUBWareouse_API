package com.jsenen.hubwarehouse.service;

import com.jsenen.hubwarehouse.domain.ServiceOrders;
import com.jsenen.hubwarehouse.repository.ServiceOrderRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ServiceOrderServiceImpl implements ServiceOrderService{

    @Autowired
    ServiceOrderRepository serviceOrderRepository;

    @Override
    public Iterable<ServiceOrders> findAll() {

        Iterable<ServiceOrders> serviceOrders = serviceOrderRepository.findAll();
        return serviceOrders;
    }

    @Override
    public ServiceOrders addNewServiceOrder(ServiceOrders serviceOrders) {
        return null;
    }
}
