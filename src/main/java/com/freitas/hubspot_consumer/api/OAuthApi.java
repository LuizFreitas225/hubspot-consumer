package com.freitas.hubspot_consumer.api;

import com.freitas.hubspot_consumer.service.AuthorizationService;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.net.URI;
@RequiredArgsConstructor
@RestController
@RequestMapping("/oauth")
@Slf4j
public class OAuthApi {
    private final AuthorizationService authorizationService;

    @GetMapping("/authorize")
    public ResponseEntity<Void> generateAuthUrl() {
        log.info("OAuthApi.generateAuthUrl - start");
        return ResponseEntity.status(HttpStatus.FOUND)
                .location(URI.create(authorizationService.generateAuthorizeUrl()))
                .build();
    }

    @GetMapping("/callback")
    public ResponseEntity<Void> callback(@RequestParam String code) {
        log.info("OAuthApi.callback - start");
        authorizationService.callback(code);
        log.info("OAuthApi.callback - end");
        return ResponseEntity.ok().build();
    }
}
