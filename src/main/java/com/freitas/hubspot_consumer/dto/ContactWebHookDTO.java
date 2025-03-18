package com.freitas.hubspot_consumer.dto;

import lombok.Data;

@Data
public class ContactWebHookDTO {
    private Long appId;
        private Long eventId;
        private Long subscriptionId;
        private Long portalId;
        private Long occurredAt;
        private String subscriptionType;
        private Integer attemptNumber;
        private Long objectId;
        private String changeSource;
        private String changeFlag;
}
