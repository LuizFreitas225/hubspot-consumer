package com.freitas.hubspot_consumer.exception;

import lombok.Getter;
import lombok.Setter;
import org.springframework.http.HttpStatus;

@Getter
public class CreateContactException extends RuntimeException {
    HttpStatus status;
    public CreateContactException(String message, HttpStatus status) {
        super(message);
        this.status = status;
    }
}
