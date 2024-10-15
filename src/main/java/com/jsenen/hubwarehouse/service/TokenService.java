package com.jsenen.hubwarehouse.service;

import com.fasterxml.jackson.annotation.JsonProperty;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.time.Instant;

@Service
public class TokenService {

    @Value("${digikey.api.client_id}")
    private String clientId;

    @Value("${digikey.api.client_secret}")
    private String clientSecret;

    @Value("${digikey.api.token_url}")
    private String tokenUrl;

    private final RestTemplate restTemplate;

    private String accessToken;
    private Instant expirationTime;

    public TokenService(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    // Método para obtener un token válido
    public String getAccessToken() {
        // Verifica si el token es válido o si ha expirado
        if (accessToken == null || Instant.now().isAfter(expirationTime)) {
            requestNewToken(); // Si no es válido o ha expirado, solicita un nuevo token
        }
        return accessToken;
    }

    // Método para solicitar un nuevo token
    private void requestNewToken() {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);

        String body = "client_id=" + clientId + "&client_secret=" + clientSecret + "&grant_type=client_credentials";

        HttpEntity<String> request = new HttpEntity<>(body, headers);

        ResponseEntity<TokenResponse> response = restTemplate.exchange(tokenUrl, HttpMethod.POST, request, TokenResponse.class);

        if (response.getStatusCode().is2xxSuccessful()) {
            this.accessToken = response.getBody().getAccessToken();
            int expiresIn = response.getBody().getExpiresIn(); // Tiempo de expiración en segundos

            // Calcula el tiempo de expiración
            this.expirationTime = Instant.now().plusSeconds(expiresIn);
        } else {
            throw new RuntimeException("Error al solicitar el token: " + response.getStatusCode());
        }
    }

    // Clase interna para mapear la respuesta del token
    private static class TokenResponse {
        @JsonProperty("access_token")
        private String accessToken;

        @JsonProperty("expires_in")
        private int expiresIn; // Tiempo de vida del token en segundos

        public String getAccessToken() {
            return accessToken;
        }

        public void setAccessToken(String accessToken) {
            this.accessToken = accessToken;
        }

        public int getExpiresIn() {
            return expiresIn;
        }

        public void setExpiresIn(int expiresIn) {
            this.expiresIn = expiresIn;
        }
    }
}
