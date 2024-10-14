package com.jsenen.hubwarehouse.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;

import java.util.Map;
@Service
public class OAuth2Service {

    @Value("${digikey.api.url}")
    private String digikeyApiUrl;

    @Value("${digikey.api.client_id}")
    private String clientId;

    @Value("${digikey.api.client_secret}")
    private String clientSecret;

    private final RestTemplate restTemplate;

    // Inyectar RestTemplate a través del constructor
    @Autowired
    public OAuth2Service(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    public String getAccessToken() {
        String url = digikeyApiUrl + "/v1/oauth2/token";
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);

        String body = "client_id=" + clientId
                + "&client_secret=" + clientSecret
                + "&grant_type=client_credentials";

        HttpEntity<String> request = new HttpEntity<>(body, headers);

        try {
            ResponseEntity<Map> response = restTemplate.exchange(url, HttpMethod.POST, request, Map.class);
            Map<String, Object> responseBody = response.getBody();
            if (responseBody != null && responseBody.containsKey("access_token")) {
                return (String) responseBody.get("access_token");
            }
        } catch (HttpClientErrorException e) {
            System.out.println("Error al solicitar el token: " + e.getResponseBodyAsString());
        }
        return null;
    }
}
