package com.jsenen.hubwarehouse.service;

import com.fasterxml.jackson.annotation.JsonProperty;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public class TokenService {

    @Value("${digikey.api.client_id}")
    private String clientId;

    @Value("${digikey.api.client_secret}")
    private String clientSecret;

    @Value("${digikey.api.token_url}")
    private String tokenUrl;

    private final RestTemplate restTemplate;

    public TokenService(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    public String getAccessToken() {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);

        String body = "client_id=" + clientId + "&client_secret=" + clientSecret + "&grant_type=client_credentials";

        HttpEntity<String> request = new HttpEntity<>(body, headers);

        ResponseEntity<TokenResponse> response = restTemplate.exchange(tokenUrl, HttpMethod.POST, request, TokenResponse.class);

        return response.getBody().getAccessToken();  // Aquí asegúrate de que obtienes el token correctamente.
    }

    // Clase interna para mapear la respuesta del token
    private static class TokenResponse {
        @JsonProperty("access_token")
        private String accessToken;

        public String getAccessToken() {
            return accessToken;
        }

        public void setAccessToken(String accessToken) {
            this.accessToken = accessToken;
        }
    }
}
