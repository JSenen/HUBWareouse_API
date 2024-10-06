package com.jsenen.hubwarehouse.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.jsenen.hubwarehouse.controller.ComponentController;
import com.jsenen.hubwarehouse.domain.Component;
import com.jsenen.hubwarehouse.domain.FarnellApiResponse;
import com.jsenen.hubwarehouse.domain.FarnellComponent;
import com.jsenen.hubwarehouse.domain.ProductResponse;
import com.jsenen.hubwarehouse.repository.ComponentRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import java.io.IOException;
import java.util.List;
import java.util.Optional;

@Service
public class ComponentServiceImpl implements ComponentService{

    private final static Logger logger = LoggerFactory.getLogger(ComponentServiceImpl.class);
    private final String TAG = "ComponentServiceImpl";

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

    @Override
    public Optional<Component> findByPartNumber(String partNumber) {
        return componentRepository.findByPartNumberComponent(partNumber);
    }

    @Override
    public FarnellComponent searchPNumberFarnell(String productNumber) {
        // La URL base de la API de Farnell (element14)
        String apiKey = "gckf97rgvrpbc2an9c3gsp8r";  // Aquí va tu API Key
        String baseUrl = "https://api.element14.com/catalog/products";

        // Construimos la URL con los parámetros necesarios
        String url = UriComponentsBuilder.fromHttpUrl(baseUrl)
                .queryParam("versionNumber", "1.3")
                .queryParam("term", "manuPartNum:" + productNumber)
                .queryParam("storeInfo.id", "es.farnell.com")
                .queryParam("resultsSettings.offset", "0")
                .queryParam("resultsSettings.numberOfResults", "1")
                .queryParam("resultsSettings.refinements.filters", "rohsCompliant,inStock")
                .queryParam("resultsSettings.responseGroup", "large")
                .queryParam("callInfo.omitXmlSchema", "false")
                .queryParam("callInfo.responseDataFormat", "json")
                .queryParam("callinfo.apiKey", apiKey)
                .queryParam("apiKey", apiKey)
                .toUriString();

        // Crear el RestTemplate para realizar la solicitud
        RestTemplate restTemplate = new RestTemplate();

        try {
            // Realizar la solicitud GET a la API
            ResponseEntity<String> response = restTemplate.getForEntity(url, String.class);

            // Verificar si la respuesta es correcta
            if (response.getStatusCode().is2xxSuccessful()) {
                // Procesar el JSON que recibiste y convertirlo en tu objeto FarnellComponent
                String jsonResponse = response.getBody();

                // Convierte el JSON de la API a tu objeto FarnellComponent
                FarnellComponent component = parseJsonToComponent(jsonResponse);
                logger.info("Respuesta completa de la API de Farnell: " + jsonResponse);
                return component;  // Devolver el componente encontrado
            } else {
                logger.error("Error al buscar en la API de Farnell. Código de estado: " + response.getStatusCode());
                return null;
            }
        } catch (Exception e) {
            logger.error("Error al buscar en la API de Farnell", e);
            return null;
        }
    }

    // Método para convertir el JSON de la API a un objeto FarnellComponent
    private FarnellComponent parseJsonToComponent(String jsonResponse) {
        try {
            ObjectMapper mapper = new ObjectMapper();
            FarnellApiResponse apiResponse = mapper.readValue(jsonResponse, FarnellApiResponse.class);

            // Obtener el primer producto de la respuesta
            if (apiResponse != null && apiResponse.getManufacturerPartNumberSearchReturn() != null) {
                List<FarnellComponent> products = apiResponse.getManufacturerPartNumberSearchReturn().getProducts();
                if (!products.isEmpty()) {
                    return products.get(0); // Retornar el primer producto
                }
            }
            return null;  // No se encontró ningún producto
        } catch (IOException e) {
            logger.error("Error al convertir JSON a FarnellComponent", e);
            return null;
        }
    }

    @Override
    public Component addNewComponentFromWeb(Component component) {
        return componentRepository.save(component);
    }
}

