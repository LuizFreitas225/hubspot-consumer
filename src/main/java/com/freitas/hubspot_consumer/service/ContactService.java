package com.freitas.hubspot_consumer.service;

import com.freitas.hubspot_consumer.dto.ContactDTO;
import com.freitas.hubspot_consumer.dto.ContactWebHookDTO;
import com.freitas.hubspot_consumer.dto.RetrieveContactDTO;
import com.freitas.hubspot_consumer.exception.*;
import com.freitas.hubspot_consumer.model.Contact;
import com.freitas.hubspot_consumer.repository.ContactRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import java.util.List;

@Slf4j
@RequiredArgsConstructor
@Service
public class ContactService {
    private final RestTemplate restTemplate;
    private final AuthorizationService authorizationService;
    private final ContactRepository contactRepository;
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

    public void consumerWebhook(List<ContactWebHookDTO> contactWebHookDTOList) {
         for (ContactWebHookDTO contactWebHookDTO : contactWebHookDTOList) {
             String url = getRetrieveUrl(contactWebHookDTO);

             HttpEntity<String> entity = new HttpEntity<>(authorizationService.createHeadersForAPI(authorizationService.getHubSpotToken()));

             try {
                 ResponseEntity<RetrieveContactDTO> response = restTemplate.exchange(url, HttpMethod.GET, entity, RetrieveContactDTO.class);
                 if ( response.getBody() != null && response.getStatusCode() == HttpStatus.OK) {
                     this.saveOrUpdate(response.getBody().toEntity());
                     log.info("Contact retrieved successfully: {}", response.getBody());
                 } else {
                     log.error("Failed to retrieve contact: {}", response.getStatusCode());

                     throw new RetrieveContactException(ExceptionMessage.FAIL_RETRIEVE_CONTACT + response.getBody(),
                             HttpStatus.valueOf(response.getStatusCode().value()));
                 }
             } catch (HttpClientErrorException e) {
                 log.error("Failed to retrieve contact: {}", e.getMessage());
                 throw new RetrieveContactException(ExceptionMessage.FAIL_RETRIEVE_CONTACT + e.getMessage(),
                         HttpStatus.valueOf(e.getStatusCode().value()));
             }
         }
    }

    private void saveOrUpdate(Contact newContact) {
        Long currentContactId = contactRepository.findIdByEmail(newContact.getEmail());
            if(currentContactId != null) {
                newContact.setId(currentContactId);
            }
        contactRepository.save(newContact);
        log.info("Contact saved successfully: {}", newContact.getEmail());
    }

    private static String getRetrieveUrl(ContactWebHookDTO contactWebHookDTO) {
        return UriComponentsBuilder.fromHttpUrl(CONTACTS_URL)
            .pathSegment(contactWebHookDTO.getObjectId().toString())
            .toUriString();
    }
}
