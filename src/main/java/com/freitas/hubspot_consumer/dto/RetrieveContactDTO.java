package com.freitas.hubspot_consumer.dto;

import com.freitas.hubspot_consumer.model.Contact;
import lombok.Data;

@Data
public class RetrieveContactDTO {
    private String id;
    private PropertiesRetrieveContactDTO properties;

    public Contact toEntity() {
        Contact contact = new Contact();
        contact.setHs_object_id(this.id);
        contact.setEmail(this.properties.getEmail());
        contact.setFirstname(this.properties.getFirstname());
        contact.setLastname(this.properties.getLastname());
        contact.setCreatedate(this.properties.getCreatedate());
        contact.setLastmodifieddate(this.properties.getLastmodifieddate());
        return contact;
    }
}