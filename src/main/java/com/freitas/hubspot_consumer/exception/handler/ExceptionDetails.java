package com.freitas.hubspot_consumer.exception.handler;

import java.time.LocalDateTime;
import lombok.Getter;


@Getter
public class ExceptionDetails {
    private final String message;
    private final int status;
    private final LocalDateTime timestamp;

    public ExceptionDetails(String message, int status) {
        this.message = message;
        this.status = status;
        this.timestamp = LocalDateTime.now();
    }
}
