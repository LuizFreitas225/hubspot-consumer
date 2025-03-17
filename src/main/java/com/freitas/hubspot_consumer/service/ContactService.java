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
public class ContactService {
    private final RestTemplate restTemplate;
    private final AuthorizationService authorizationService;
    private static final String CONTACTS_URL = "https://api.hubapi.com/crm/v3/objects/contacts";


    public void createContact(ContactDTO contactDTO) {
        HttpEntity<String> entity = new HttpEntity<>(contactDTO.toJson(),
                authorizationService.createHeadersForAPI(authorizationService.getHubSpotToken()));
        ResponseEntity<String> response = restTemplate.exchange(CONTACTS_URL, HttpMethod.POST, entity, String.class);

        if (response.getStatusCode() != HttpStatus.CREATED) {
            throw new CreateContactException(ExceptionMessage.FAIL_CREATE_CONTACT,
                    HttpStatus.valueOf(response.getStatusCode().value()));
        }
    }
}
