package com.jsenen.hubwarehouse.service;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.http.*;
import org.springframework.web.client.RestTemplate;
import com.jsenen.hubwarehouse.domain.ProductResponse;

import java.util.Collections;

@Service
public class ExternalApiService {

    private final RestTemplate restTemplate;
    private final TokenService tokenService;

    @Value("${api.external.url}")
    private String externalApiUrl;

    public ExternalApiService(RestTemplate restTemplate, TokenService tokenService) {
        this.restTemplate = restTemplate;
        this.tokenService = tokenService;
    }

    public ProductResponse getComponentData(String partNumber) {
        // Obtener el token de acceso
        String accessToken = tokenService.getAccessToken();

        // Crear los headers con el token de autorización y otros headers requeridos
        HttpHeaders headers = new HttpHeaders();
        headers.set("Authorization", "Bearer " + accessToken);
        headers.set("X-DIGIKEY-Client-Id", "HTCECBeByPafNhlANWJma2Qi1m2gxs3B");
        headers.set("X-DIGIKEY-Locale-Language", "en");
        headers.set("X-DIGIKEY-Locale-Currency", "USD");
        headers.set("X-DIGIKEY-Locale-Site", "us");
        headers.set("X-DIGIKEY-Customer-Id", "123456");
        headers.setAccept(Collections.singletonList(MediaType.APPLICATION_JSON));

        HttpEntity<String> entity = new HttpEntity<>(headers);

        // Construir la URL correctamente con el part number
        String url = externalApiUrl + "/" + partNumber + "/productdetails";
        //TODO Clean after debug
        System.out.println("Request URL: " + url);


        // Hacer la solicitud a la API externa
        ResponseEntity<ProductResponse> response = restTemplate.exchange(
                url,
                HttpMethod.GET,
                entity,
                ProductResponse.class
        );

        return response.getBody();
    }
}
