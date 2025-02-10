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

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
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
            logger.info("Fetching Mouser data for part number: {}", productNumber);

            // Configurar Headers y Cuerpo
            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_JSON);

            String requestBody = "{ \"SearchByPartRequest\": { \"mouserPartNumber\": \"" + productNumber + "\", \"partSearchOptions\": \"string\" } }";
            HttpEntity<String> requestEntity = new HttpEntity<>(requestBody, headers);

            String url = apiMouserUrl + "?apiKey=" + apiKey;

            // Llamada al backend de Mouser
            ResponseEntity<String> response = restTemplate.exchange(url, HttpMethod.POST, requestEntity, String.class);

            // Parsear el JSON
            ObjectMapper objectMapper = new ObjectMapper();
            JsonNode root = objectMapper.readTree(response.getBody());
            JsonNode parts = root.path("SearchResults").path("Parts");

            if (parts.isEmpty()) {
                logger.warn("No parts found for part number: {}", productNumber);
                return null;
            }

            JsonNode firstPart = parts.get(0);

            // Mapear los datos a Component
            Component component = new Component();
            component.setPartNumberComponent(firstPart.path("MouserPartNumber").asText());
            component.setDescriptionComponent(firstPart.path("Description").asText());
            component.setDetailDescriptionCmp(firstPart.path("Category").asText());
            component.setManufacturerComponent(firstPart.path("Manufacturer").asText());
            component.setImage(firstPart.path("ImagePath").asText());
            component.setDatasheets(firstPart.path("DataSheetUrl").asText());

            // Mapear atributos técnicos como JSON
            List<String> technicalAttributes = new ArrayList<>();
            JsonNode attributes = firstPart.path("ProductAttributes");
            for (JsonNode attr : attributes) {
                String attrName = attr.path("AttributeName").asText();
                String attrValue = attr.path("AttributeValue").asText();
                technicalAttributes.add("{\"AttributeName\":\"" + attrName + "\",\"AttributeValue\":\"" + attrValue + "\"}");
            }
            component.setTechnicalAttributes(technicalAttributes.toString());

            logger.info("Successfully fetched Mouser component: {}", component.getPartNumberComponent());
            return component;

        } catch (Exception e) {
            logger.error("Error fetching data from Mouser API", e);
            return null;
        }
    }

}

