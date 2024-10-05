package com.jsenen.hubwarehouse.service;

import com.jsenen.hubwarehouse.domain.Attribute;
import com.jsenen.hubwarehouse.domain.FarnellComponent;
import com.jsenen.hubwarehouse.domain.Image;
import com.jsenen.hubwarehouse.domain.Price;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.List;
import java.util.Map;

@Service
public class ExternalFarnellService {

    private final RestTemplate restTemplate;

    @Value("${api.externalfarnell.url}")
    private String externalfarnell;

    @Value("${api.externalfarnell.apikey}")
    private String apiKey;

    public ExternalFarnellService(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    public FarnellComponent getComponentData(String partNumber) {

        // Codificar correctamente el partNumber para evitar problemas con caracteres especiales
        String encodedPartNumber = URLEncoder.encode("manuPartNum:" + partNumber, StandardCharsets.UTF_8);

        // Construcción de la URL con UriComponentsBuilder para mayor legibilidad y control
        String url = UriComponentsBuilder.fromHttpUrl(externalfarnell)
                .queryParam("versionNumber", "1.3")
                .queryParam("term", encodedPartNumber)
                .queryParam("storeInfo.id", "uk.farnell.com")
                .queryParam("resultsSettings.offset", "0")
                .queryParam("resultsSettings.numberOfResults", "1")
                .queryParam("resultsSettings.refinements.filters", "rohsCompliant,inStock")
                .queryParam("resultsSettings.responseGroup", "large")
                .queryParam("callInfo.omitXmlSchema", "false")
                .queryParam("callInfo.responseDataFormat", "json")
                .queryParam("apiKey", apiKey) // API Key solo una vez
                .toUriString();

        System.out.println("Request URL: " + url);

        // Establecer los encabezados
        HttpHeaders headers = new HttpHeaders();
        headers.set("Accept", MediaType.APPLICATION_JSON_VALUE);
        headers.set("User-Agent", "Mozilla/5.0"); // Añadir un User-Agent válido
        HttpEntity<String> entity = new HttpEntity<>(headers);

        try {
            // Realizar la solicitud GET
            ResponseEntity<Map> response = restTemplate.exchange(
                    url,
                    HttpMethod.GET,
                    entity,
                    Map.class
            );

            System.out.println("RESPONSE: " + response.getBody());

            if (response.getBody() != null) {
                // Extraer el resultado
                Map<String, Object> searchReturn = (Map<String, Object>) response.getBody().get("keywordSearchReturn");
                List<Map<String, Object>> products = (List<Map<String, Object>>) searchReturn.get("products");
                if (!products.isEmpty()) {
                    // Mapeamos el primer producto
                    return mapToFarnellComponent(products.get(0));
                }
            }

        } catch (HttpClientErrorException e) {
            // Manejo de errores, por ejemplo, si la API responde con 4XX o 5XX
            System.out.println("Error en la solicitud: " + e.getStatusCode() + " - " + e.getResponseBodyAsString());
            throw e;
        }

        return null; // En caso de que no se encuentren productos
    }

    private FarnellComponent mapToFarnellComponent(Map<String, Object> productData) {
        FarnellComponent component = new FarnellComponent();
        component.setSku((String) productData.get("sku"));
        component.setDisplayName((String) productData.get("displayName"));
        component.setProductURL((String) productData.get("productURL"));
        component.setProductStatus((String) productData.get("productStatus"));
        component.setRohsStatusCode((String) productData.get("rohsStatusCode"));
        component.setPackSize((Integer) productData.get("packSize"));
        component.setUnitOfMeasure((String) productData.get("unitOfMeasure"));
        component.setVendorName((String) productData.get("vendorName"));
        component.setBrandName((String) productData.get("brandName"));

        // Manejo de precios
        List<Map<String, Object>> pricesData = (List<Map<String, Object>>) productData.get("prices");
        if (pricesData != null) {
            List<Price> prices = pricesData.stream().map(price -> new Price(
                    (Integer) price.get("from"),
                    (Integer) price.get("to"),
                    (Double) price.get("cost"),
                    (Double) price.get("costIncTax")
            )).toList();
            component.setPrices(prices);
        }

        // Manejo de imagen
        Map<String, Object> imageData = (Map<String, Object>) productData.get("image");
        if (imageData != null) {
            Image image = new Image(
                    (String) imageData.get("baseName"),
                    (String) imageData.get("mainImageURL"),
                    (String) imageData.get("thumbNailImageURL")
            );
            component.setImage(image);
        }

        // Manejo de atributos
        List<Map<String, Object>> attributesData = (List<Map<String, Object>>) productData.get("attributes");
        if (attributesData != null) {
            List<Attribute> attributes = attributesData.stream().map(attribute -> new Attribute(
                    (String) attribute.get("attributeLabel"),
                    (String) attribute.get("attributeValue"),
                    (String) attribute.get("attributeUnit")
            )).toList();
            component.setAttributes(attributes);
        }

        return component;
    }
}
