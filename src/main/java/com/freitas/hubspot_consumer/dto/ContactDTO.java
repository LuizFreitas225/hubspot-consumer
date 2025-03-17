package com.freitas.hubspot_consumer.dto;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.freitas.hubspot_consumer.exception.ExceptionMessage;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.Data;

@Data
public class ContactDTO {

    @NotBlank(message = ExceptionMessage.EMAIL_MANDATORY)
    @Email(message = ExceptionMessage.EMAIL_INVALID)
    private String email;

    @NotBlank(message = ExceptionMessage.FIRSTNAME_MANDATORY)
    private String firstname;

    @NotBlank(message = ExceptionMessage.LASTNAME_MANDATORY)
    private String lastname;

    @Pattern(regexp = "^\\(\\d{2}\\)\\d{8,9}$", message = ExceptionMessage.PHONE_INVALID)
    private String phone;

  private String company;

  private String website;

  private String lifecyclestage;

   public String toJson() {
       ObjectMapper mapper = new ObjectMapper();
       ObjectNode properties = mapper.createObjectNode();
       properties.put("email", this.getEmail());
       properties.put("firstname", this.getFirstname());
       properties.put("lastname", this.getLastname());
       properties.put("phone", this.getPhone());

       if (this.getCompany() != null && !this.getCompany().isEmpty()) {
           properties.put("company", this.getCompany());
       }
       if (this.getWebsite() != null && !this.getWebsite().isEmpty()) {
           properties.put("website", this.getWebsite());
       }
       properties.put("lifecyclestage", this.getLifecyclestage());

       ObjectNode jsonObject = mapper.createObjectNode();
       jsonObject.set("properties", properties);

       return jsonObject.toString();
   }
}