package com.quotelab.backend.application.client;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class CreateClientRequest {
    @NotBlank
    private String name;

    @NotBlank
    @Email
    private String email;

    private String phone;
    private String company;
    private String document;
    private String notes;
}