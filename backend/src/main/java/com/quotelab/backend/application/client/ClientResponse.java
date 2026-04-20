package com.quotelab.backend.application.client;

import lombok.Data;
import java.time.OffsetDateTime;
import java.util.UUID;

@Data
public class ClientResponse {
    private UUID id;
    private String name;
    private String email;
    private String phone;
    private String company;
    private String document;
    private String notes;
    private OffsetDateTime createdAt;
}