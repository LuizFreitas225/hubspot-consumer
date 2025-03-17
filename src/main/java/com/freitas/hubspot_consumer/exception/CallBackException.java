package com.freitas.hubspot_consumer.exception;

import lombok.Getter;
import lombok.Setter;
import org.springframework.http.HttpStatus;

@Getter
public class CallBackException  extends RuntimeException {
    HttpStatus status;
    public CallBackException(String message, HttpStatus status) {
        super(message);
        this.status = status;
    }
}
