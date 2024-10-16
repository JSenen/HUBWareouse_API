package com.jsenen.hubwarehouse.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.jsenen.hubwarehouse.controller.ComponentController;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import com.jsenen.hubwarehouse.domain.Component;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;

import java.util.List;
import java.util.Map;

@Service
public class DigikeyService {

    private final static Logger logger = LoggerFactory.getLogger(DigikeyService.class);
    private final String TAG = "DigiKeyService";

    @Autowired
    private OAuth2Service oAuth2Service;

    @Autowired
    private RestTemplate restTemplate;

    @Value("${digikey.api.url}")
    private String digikeyApiUrl;

    @Value("${digikey.api.client_id}")
    private String clientId;

    public Component getComponentData(String partNumber) {
        String token = oAuth2Service.getAccessToken();
        logger.info("TOKEN: " + token, TAG);

        if (token == null) {
            System.out.println("No se pudo obtener el token OAuth2.");
            return null;
        }

        String url = digikeyApiUrl + "/products/v4/search/" + partNumber + "/productdetails";
        HttpHeaders headers = new HttpHeaders();
        headers.setBearerAuth(token);
        headers.set("X-DIGIKEY-Client-Id", clientId);
        headers.set("Accept", MediaType.APPLICATION_JSON_VALUE);

        HttpEntity<String> request = new HttpEntity<>(headers);

        try {
            ResponseEntity<Map> response = restTemplate.exchange(url, HttpMethod.GET, request, Map.class);
            if (response.getBody() != null) {
                logger.info("JSON DIGIKEY: " + response, TAG);
                return mapToComponent(response.getBody());
            }
        } catch (HttpClientErrorException e) {
            System.out.println("Error al solicitar datos del componente: " + e.getResponseBodyAsString());
        }
        return null;
    }

    private Component mapToComponent(Map<String, Object> productData) {
        Component component = new Component();

        // Mapear el producto a los atributos del componente
        Map<String, Object> product = (Map<String, Object>) productData.get("Product");

        component.setPartNumberComponent((String) product.get("ManufacturerProductNumber"));

        Map<String, Object> description = (Map<String, Object>) product.get("Description");
        component.setDescriptionComponent((String) description.get("ProductDescription"));
        component.setDetailDescriptionCmp((String) description.get("DetailedDescription"));

        Map<String, Object> manufacturer = (Map<String, Object>) product.get("Manufacturer");
        component.setManufacturerComponent((String) manufacturer.get("Name"));

        // Guardar la URL de la imagen del producto directamente como String
        String imageUrl = (String) product.get("PhotoUrl");

        component.setImage(imageUrl);  // Asignar directamente la URL en lugar de descargar la imagen


        // Mapear parámetros técnicos
        List<Map<String, Object>> parameters = (List<Map<String, Object>>) product.get("Parameters");
        ObjectMapper mapper = new ObjectMapper();
        try {
            // Convertir la lista de parámetros a JSON y almacenarla en technicalAttributes
            String technicalAttributesJson = mapper.writeValueAsString(parameters);
            component.setTechnicalAttributes(technicalAttributesJson);
        } catch (Exception e) {
            e.printStackTrace();
        }

        return component;
    }


}
