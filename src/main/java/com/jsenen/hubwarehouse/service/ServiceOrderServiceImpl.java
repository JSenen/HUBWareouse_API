package com.jsenen.hubwarehouse.service;

import com.jsenen.hubwarehouse.controller.ComponentController;
import com.jsenen.hubwarehouse.domain.Component;
import com.jsenen.hubwarehouse.domain.ServiceOrderComponent;
import com.jsenen.hubwarehouse.domain.ServiceOrders;
import com.jsenen.hubwarehouse.exception.EntityNotFound;
import com.jsenen.hubwarehouse.repository.ComponentRepository;
import com.jsenen.hubwarehouse.repository.ServiceOrderRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

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
        serviceOrderRepository.save(serviceOrders);
        logger.info("ServiceOrders JSON : " + serviceOrders);
        return serviceOrders;
    }

    @Override
    public void deleteServiceOrder(long id) {
        serviceOrderRepository.deleteById(id);
    }

    @Override
    public Optional<ServiceOrders> findById(long idLong) {
        return serviceOrderRepository.findById(idLong);
    }

    @Override
    public ServiceOrders updateServiceOrder(long id, ServiceOrders serviceOrders) throws EntityNotFound {
        logger.info("Update Service Order id : " + id + " Service Order --> " + serviceOrders);

        // Recuperar la orden de servicio existente desde la base de datos
        ServiceOrders serviceOrderToEdit = serviceOrderRepository.findById(id)
                .orElseThrow(() -> new EntityNotFound("La orden de servicio no se encuentra"));


        // Actualizar los campos principales
        if (serviceOrders.getStatus() != null) {
            serviceOrderToEdit.setStatus(serviceOrders.getStatus());
        }

        if (serviceOrders.getDateStart() != null) {
            serviceOrderToEdit.setDateStart(serviceOrders.getDateStart());
        }

        if (serviceOrders.getDateFinish() != null) {
            serviceOrderToEdit.setDateFinish(serviceOrders.getDateFinish());
        }

        // Manejar los componentes si se proporcionan
        if (serviceOrders.getServiceOrderComponents() != null) {
            List<ServiceOrderComponent> existingComponents = serviceOrderToEdit.getServiceOrderComponents();
            List<ServiceOrderComponent> updatedComponents = new ArrayList<>();

            for (ServiceOrderComponent newComponent : serviceOrders.getServiceOrderComponents()) {
                if (newComponent.getId() == null) {
                    // Componente nuevo, agregarlo
                    Component componentFromDb = componentRepository.findById(newComponent.getComponent().getIdComponent())
                            .orElseThrow(() -> new EntityNotFound("El componente no se encuentra"));
                    newComponent.setComponent(componentFromDb);
                    newComponent.setServiceOrder(serviceOrderToEdit);
                    updatedComponents.add(newComponent);
                } else {
                    // Componente existente, actualizar cantidad
                    ServiceOrderComponent existingComponent = existingComponents.stream()
                            .filter(comp -> comp.getId().equals(newComponent.getId()))
                            .findFirst()
                            .orElseThrow(() -> new EntityNotFound("Componente no encontrado"));

                    existingComponent.setQuantity(newComponent.getQuantity());

                    // Añadir el componente actualizado a la nueva lista
                    updatedComponents.add(existingComponent);
                }
            }

            // Establecer la nueva lista de componentes actualizada
            serviceOrderToEdit.getServiceOrderComponents().clear();
            serviceOrderToEdit.getServiceOrderComponents().addAll(updatedComponents);
        }

        // Guardar la orden de servicio actualizada
        return serviceOrderRepository.save(serviceOrderToEdit);
    }

    @Override
    public List<ServiceOrders> searchByComponentId(long idComponent) {
        return serviceOrderRepository.findByComponentId(idComponent);
    }


}


