package com.freitas.hubspot_consumer.dto;

import lombok.Data;

import java.time.ZonedDateTime;

@Data
public class PropertiesRetrieveContactDTO {
    private ZonedDateTime createdate;
    private String email;
    private String firstname;
    private String hs_object_id;
    private ZonedDateTime lastmodifieddate;
    private String lastname;
}
