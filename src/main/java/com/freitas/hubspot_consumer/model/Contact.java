package com.freitas.hubspot_consumer.model;

import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDateTime;
import java.time.ZonedDateTime;

@Table(name = "\"contact\"")
@Entity
@Data
public class Contact {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "createdate")
    private ZonedDateTime createdate;

    @Column(name = "email")
    private String email;

    @Column(name = "firstname")
    private String firstname;

    @Column(name = "hs_object_id")
    private String hs_object_id;

    @Column(name = "lastmodifieddate")
    private ZonedDateTime lastmodifieddate;

    @Column(name = "lastname")
    private String lastname;
}
