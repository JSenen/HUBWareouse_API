package com.jsenen.hubwarehouse.service;

import com.jsenen.hubwarehouse.controller.ComponentController;
import com.jsenen.hubwarehouse.domain.Component;
import com.jsenen.hubwarehouse.domain.ServiceOrderComponent;
import com.jsenen.hubwarehouse.domain.ServiceOrders;
import com.jsenen.hubwarehouse.repository.ComponentRepository;
import com.jsenen.hubwarehouse.repository.ServiceOrderRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;

@Service
public class ServiceOrderServiceImpl implements ServiceOrderService{

    private final static Logger logger = LoggerFactory.getLogger(ServiceOrderServiceImpl.class);
    private final String TAG = "ServiceOrderServiceImpl";

    @Autowired
    ServiceOrderRepository serviceOrderRepository;

    @Autowired
    ComponentRepository componentRepository;


    @Override
    public Iterable<ServiceOrders> findAll() {

        Iterable<ServiceOrders> serviceOrders = serviceOrderRepository.findAll();
        return serviceOrders;
    }

    @Override
    public ServiceOrders addNewServiceOrder(ServiceOrders serviceOrders) {


        // Si la lista de componentes es nula, inicializarla
        if (serviceOrders.getServiceOrderComponents() == null) {
            serviceOrders.setServiceOrderComponents(new ArrayList<>());
        }

        // Para cada componente en la orden de servicio
        for (ServiceOrderComponent soc : serviceOrders.getServiceOrderComponents()) {
            logger.info("Procesando componente con id: " + soc.getComponent().getIdComponent());
            // Recuperar el componente desde la base de datos usando su id
            Component component = componentRepository.findById(soc.getComponent().getIdComponent())
                    .orElseThrow(() -> new RuntimeException("Componente no encontrado"));

            // Asociar el componente con la orden de servicio
            soc.setServiceOrder(serviceOrders);

            // Establecer el componente que se va a guardar
            soc.setComponent(component);
        }
        return serviceOrders;
    }

    @Override
    public void deleteServiceOrder(long id) {
        serviceOrderRepository.deleteById(id);
    }

}
