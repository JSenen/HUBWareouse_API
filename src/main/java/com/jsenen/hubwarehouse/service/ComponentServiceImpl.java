package com.jsenen.hubwarehouse.service;

import com.jsenen.hubwarehouse.domain.Component;
import com.jsenen.hubwarehouse.domain.ProductResponse;
import com.jsenen.hubwarehouse.repository.ComponentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ComponentServiceImpl implements ComponentService{

    @Autowired
    ComponentRepository componentRepository;
    @Autowired
    ExternalApiService externalApiService;

    @Override
    public Iterable<Component> findAll() {

        Iterable<Component> componentList = componentRepository.findAll();
        return  componentList;

    }

    @Override
    public Component addNewComponent(Component component) {
        // Llamada a la API externa para obtener los detalles del componente
        ProductResponse productResponse = externalApiService.getComponentData(component.getPartNumberComponent());

        // Rellenar el componente con los datos obtenidos de la API externa
        component.setDescriptionComponent(productResponse.getProduct().getDescription().getProductDescription());
        component.setDetailDescriptionCmp(productResponse.getProduct().getDescription().getDetailedDescription());
        component.setManufacturerComponent(productResponse.getProduct().getManufacturer().getName());

        // Ahora guardamos el componente en la base de datos
        Component componentNew = componentRepository.save(component);

        return componentNew;
    }

    @Override
    public void deleteComponent(long id) {
        componentRepository.deleteById(id);

    }
}
