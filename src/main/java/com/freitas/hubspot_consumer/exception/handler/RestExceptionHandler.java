package com.freitas.hubspot_consumer.exception.handler;

import com.freitas.hubspot_consumer.exception.CallBackException;
import com.freitas.hubspot_consumer.exception.CreateContactException;
import com.freitas.hubspot_consumer.exception.WithoutTokenException;
import com.freitas.hubspot_consumer.util.AuthorizationUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.net.URI;
import java.util.stream.Collectors;

@Slf4j
@ControllerAdvice
public class RestExceptionHandler extends ResponseEntityExceptionHandler {
    @ExceptionHandler(CallBackException.class)
    public ResponseEntity<Object> callBackException(CallBackException exception) {
            return new ResponseEntity<>(new ExceptionDetails(exception.getMessage(), exception.getStatus().value()),
                exception.getStatus());
    }
    @ExceptionHandler(HttpClientErrorException.class)
    public ResponseEntity<Object> httpClientErrorException(HttpClientErrorException exception) {
        HttpStatus status = HttpStatus.valueOf(exception.getMessage().contains("409") ? HttpStatus.CONFLICT.value() : HttpStatus.INTERNAL_SERVER_ERROR.value());
        return new ResponseEntity<>(new ExceptionDetails(exception.getMessage(), status.value()), status);
    }

    @ExceptionHandler(WithoutTokenException.class)
    public ResponseEntity<Object> withoutTokenException(WithoutTokenException exception) {
        log.error("Invalid or non-existent token: Status = {}, Response = {}",  exception.getStatus(), exception.getMessage());

        return ResponseEntity.status(HttpStatus.FOUND)
                .location(URI.create(exception.getLocationUrl()))
                .build();
    }

    @ExceptionHandler(CreateContactException.class)
    public ResponseEntity<Object> createContactException(CreateContactException exception) {
        log.error("Error processing create contact: Status = {}, Response = {}",  exception.getStatus(), exception.getMessage());
        return new ResponseEntity<>(new ExceptionDetails(exception.getMessage(), exception.getStatus().value()),
                exception.getStatus());
    }

    @Override
    protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex, HttpHeaders headers, HttpStatusCode status, WebRequest request) {
        String errors = ex.getBindingResult().getAllErrors().stream().map(
                error -> error.getDefaultMessage()).collect(Collectors.toList()).toString();
        return new ResponseEntity(new ExceptionDetails(errors, status.value()), status);
    }
}
