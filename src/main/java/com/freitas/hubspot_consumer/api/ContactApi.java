package com.freitas.hubspot_consumer.api;

import com.freitas.hubspot_consumer.dto.ContactDTO;
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

@RequiredArgsConstructor
@RestController
@RequestMapping("/contact")
@Slf4j
public class ContactApi {
    private final ContactService contactService;

    @PostMapping
    public ResponseEntity<Void> createContact(@RequestBody @Valid ContactDTO contactDTO) {
        log.info("ContactApi.createContact - start: {}", contactDTO);
        contactService.createContact(contactDTO);
        log.info("ContactApi.createContact - end");
        return ResponseEntity.status(HttpStatus.CREATED).build();

    }

//    @GetMapping("/callback")
//    public ResponseEntity<Void> callback(@RequestParam String code) {
//        log.info("OAuthApi.callback - start");
//        authorizationService.callback(code);
//        log.info("OAuthApi.callback - end");
//        return ResponseEntity.ok().build();
//    }
}
