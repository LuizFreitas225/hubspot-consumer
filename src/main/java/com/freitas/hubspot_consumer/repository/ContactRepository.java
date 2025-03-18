package com.freitas.hubspot_consumer.repository;

import com.freitas.hubspot_consumer.model.Contact;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface  ContactRepository extends JpaRepository<Contact, Long> {

    @Query("SELECT c.id FROM Contact c WHERE c.email = :email")
    Long findIdByEmail(String email);

}
