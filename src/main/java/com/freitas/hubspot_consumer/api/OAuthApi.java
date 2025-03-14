package com.freitas.hubspot_consumer.api;

import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import java.io.BufferedReader;
import java.io.IOException;
import java.net.URI;

@RestController
@RequestMapping("/oauth")
public class OAuthApi {

    private final String authUrl = "https://app.hubspot.com/oauth/authorize";
    private final String clientId = "c3688f97-d2af-4871-8814-fe9799f57a56";
    private final String redirectUri = "http://localhost:8080/oauth/callback";
    private final String scopes = "crm.objects.contacts.write%20oauth%20crm.objects.contacts.read";
    private final String clientSecret = "827e1c29-17b6-4c8b-b0f2-42ff07655977";

    private final String tokenUrl = "https://api.hubapi.com/oauth/v1/token";

    @GetMapping("/authorize")
    public ResponseEntity<Void> generateAuthUrl() {
        String url = UriComponentsBuilder.fromHttpUrl(authUrl)
                .queryParam("client_id", clientId)
                .queryParam("redirect_uri", redirectUri)
                .queryParam("scope", scopes)
                .queryParam("response_type", "code")
                .toUriString();

        return ResponseEntity.status(HttpStatus.FOUND)
                .location(URI.create(url))
                .build();
    }

    @GetMapping("/callback")
    public ResponseEntity<Void> callback(@RequestParam String code) {
        String url = UriComponentsBuilder.fromHttpUrl(tokenUrl)
                .queryParam("grant_type", "authorization_code")
                .queryParam("client_id", clientId)
                .queryParam("client_secret", clientSecret)
                .queryParam("redirect_uri", redirectUri)
                .queryParam("scope", scopes)
                .queryParam("code", code)
                .toUriString();

        RestTemplate restTemplate = new RestTemplate();
        ResponseEntity responseEntity = restTemplate.exchange(url, HttpMethod.POST, null, String.class);
        return ResponseEntity.ok().build();
    }
}
