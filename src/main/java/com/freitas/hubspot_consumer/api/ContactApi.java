package com.freitas.hubspot_consumer.api;

import com.freitas.hubspot_consumer.dto.ContactDTO;
import com.freitas.hubspot_consumer.dto.ContactWebHookDTO;
import com.freitas.hubspot_consumer.service.ContactService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RequiredArgsConstructor
@RestController
@RequestMapping("/contact")
@Slf4j
public class ContactApi {
    private final ContactService contactService;

    @PostMapping
    public ResponseEntity<Void> createContact(@RequestBody @Valid ContactDTO contactDTO) {
        log.info("ContactApi.createContact - start: {}", contactDTO.getEmail());
        contactService.createContact(contactDTO);
        log.info("ContactApi.createContact - end: {}", contactDTO.getEmail());
        return ResponseEntity.status(HttpStatus.CREATED).build();

    }

    @PostMapping("/webhook")
    public ResponseEntity<String> consumerWebhook(@RequestBody List<ContactWebHookDTO> contactWebHookDTOList) {
        log.info("ContactApi.consumerWebhook: {}", contactWebHookDTOList.stream().map(ContactWebHookDTO::getAppId).toList());
        contactService.consumerWebhook(contactWebHookDTOList);
        log.info("ContactApi.consumerWebhook: {}", contactWebHookDTOList.stream().map(ContactWebHookDTO::getAppId).toList());
        return ResponseEntity.ok().build();
    }

}
