package com.freitas.hubspot_consumer.exception;

import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
public class RetrieveContactException extends RuntimeException {
    HttpStatus status;
    public RetrieveContactException(String message, HttpStatus status) {
        super(message);
        this.status = status;
    }
}
