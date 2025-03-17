package com.freitas.hubspot_consumer.service;

import com.freitas.hubspot_consumer.dto.ContactDTO;
import com.freitas.hubspot_consumer.dto.HubSpotToken;
import com.freitas.hubspot_consumer.exception.CallBackException;
import com.freitas.hubspot_consumer.exception.CreateContactException;
import com.freitas.hubspot_consumer.exception.ExceptionMessage;
import com.freitas.hubspot_consumer.exception.WithoutTokenException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import java.time.LocalDateTime;
import java.util.Collections;

@Slf4j
@RequiredArgsConstructor
@Service
public class AuthorizationService {

    @Value("${oauth.auth-url}")
    private String authorizeUrl;

    @Value("${oauth.client-id}")
    private String clientId;

    @Value("${oauth.redirect-uri}")
    private String redirectUri;

    @Value("${oauth.scopes}")
    private String scopes;

    @Value("${oauth.client-secret}")
    private String clientSecret;

    @Value("${oauth.token-url}")
    private String tokenUrl;

    private final RestTemplate restTemplate;
    private HubSpotToken hubSpotToken = null;

    private static final String CONTACTS_URL = "https://api.hubapi.com/crm/v3/objects/contacts";



    public void callback(String code) {
        try {
            hubSpotToken =  restTemplate.postForObject(this.generateCallBackUrl(code), null,
                    HubSpotToken.class);
        } catch (HttpClientErrorException e) {
            log.error("Error processing callback: {}", e.getMessage());
            throw new CallBackException(ExceptionMessage.FAIL_CALL_BACK,
                        HttpStatus.valueOf(e.getStatusCode().value()));
        } catch (Exception e) {
            log.error("Error processing callback: {}", e.getMessage());
            throw new CallBackException(ExceptionMessage.ERROR_CALL_BACK, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    public String generateAuthorizeUrl() {
        return UriComponentsBuilder.fromHttpUrl(authorizeUrl)
                .queryParam("client_id", clientId)
                .queryParam("redirect_uri", redirectUri)
                .queryParam("scope", scopes)
                .toUriString();
    }

   protected String getHubSpotToken() {
      if(hubSpotToken == null || hubSpotToken.getExpirationDate().isBefore(LocalDateTime.now())) {
         throw new WithoutTokenException(ExceptionMessage.WITHOUT_TOKEN, this.generateAuthorizeUrl(),  HttpStatus.UNAUTHORIZED);
      }
      return hubSpotToken.getAccessToken();
   }


    private String generateCallBackUrl(String code) {
        return UriComponentsBuilder.fromHttpUrl(tokenUrl)
                .queryParam("grant_type", "authorization_code")
                .queryParam("client_id", clientId)
                .queryParam("client_secret", clientSecret)
                .queryParam("redirect_uri", redirectUri)
                .queryParam("code", code)
                .toUriString();
    }

    protected HttpHeaders createHeadersForAPI(String token) {
        HttpHeaders headers = new HttpHeaders();
        headers.setBearerAuth(token);
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.setAccept(Collections.singletonList(MediaType.APPLICATION_JSON));
        return headers;
    }
}
