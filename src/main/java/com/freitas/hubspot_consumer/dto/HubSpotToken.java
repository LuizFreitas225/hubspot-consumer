package com.freitas.hubspot_consumer.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import lombok.Getter;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;

@Data
public class HubSpotToken implements Serializable {
    @JsonProperty("access_token")
    private String accessToken;
    @JsonProperty("refresh_token")
    private String refreshToken;
    @JsonProperty("expires_in")
    private int expiresIn;
    private LocalDateTime createdAt;
    private LocalDateTime expirationDate;

    public HubSpotToken(String accessToken, String refreshToken, int expiresIn) {
        this.accessToken = accessToken;
        this.refreshToken = refreshToken;
        this.expiresIn = expiresIn;
        this.createdAt = LocalDateTime.now();
        // Subtracting 1 minute to ensure that the token is not expired when the request is made
//        this.expirationDate = createdAt.plusSeconds(expiresIn).minusMinutes(1);
    }
}
