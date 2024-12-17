package com.jsenen.hubwarehouse.service;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.jsenen.hubwarehouse.domain.Component;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.HashMap;
import java.util.Map;

@Service
public class MouserService {

    @Value("${api.mouserapi.url}")
    private String apiMouserUrl;

    @Value("${api.mouser.apikey}")
    private String apiKey;

    private final static Logger logger = LoggerFactory.getLogger(MouserService.class);
    private final RestTemplate restTemplate = new RestTemplate();

    public Component getComponentData(String productNumber) {
        try {
            logger.info("Fetching data from Mouser API for part number: {}", productNumber);

            // Configuración de los headers
            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_JSON);
            headers.setAccept(java.util.List.of(MediaType.APPLICATION_JSON));

            // Cuerpo de la petición JSON
            Map<String, Object> requestBody = new HashMap<>();
            Map<String, String> searchRequest = new HashMap<>();
            searchRequest.put("mouserPartNumber", productNumber);
            searchRequest.put("partSearchOptions", "string");
            requestBody.put("SearchByPartRequest", searchRequest);

            HttpEntity<Map<String, Object>> entity = new HttpEntity<>(requestBody, headers);

            // URL con API Key
            String url = apiMouserUrl + "?apiKey=" + apiKey;

            // Realizar la petición
            ResponseEntity<String> response = restTemplate.exchange(
                    url, HttpMethod.POST, entity, String.class);

            // Parsear la respuesta JSON
            ObjectMapper mapper = new ObjectMapper();
            JsonNode root = mapper.readTree(response.getBody());
            JsonNode parts = root.path("SearchResults").path("Parts");

            if (parts.isEmpty()) {
                logger.warn("No parts found for part number: {}", productNumber);
                return null;
            }

            JsonNode part = parts.get(0); // Tomamos el primer resultado

            // Mapear los datos a un objeto Component
            Component component = new Component();
            component.setPartNumberComponent(part.path("MouserPartNumber").asText());
            component.setDescriptionComponent(part.path("Description").asText());
            component.setDetailDescriptionCmp(part.path("Category").asText());
            component.setManufacturerComponent(part.path("Manufacturer").asText());
            component.setImage(part.path("ImagePath").asText());
            component.setDatasheets(part.path("DataSheetUrl").asText());

            // Atributos técnicos adicionales
            component.setTechnicalAttributes(part.path("ProductAttributes").toString());

            logger.info("Successfully retrieved component data: {}", productNumber);
            return component;

        } catch (Exception e) {
            logger.error("Error fetching component data from Mouser API", e);
            return null;
        }
    }
}
