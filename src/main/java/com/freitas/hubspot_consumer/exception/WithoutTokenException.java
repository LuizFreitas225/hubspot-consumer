package com.freitas.hubspot_consumer.exception;

import lombok.Getter;
import lombok.Setter;
import org.springframework.http.HttpStatus;

@Getter

public class WithoutTokenException extends RuntimeException {
    private HttpStatus status;
    private String locationUrl;

    public WithoutTokenException(String message, String locationUrl, HttpStatus status) {
        super(message);
        this.status = status;
        this.locationUrl = locationUrl;
    }
}
