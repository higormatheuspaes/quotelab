package com.quotelab.backend.api.v1;

import java.util.List;
import java.util.UUID;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.quotelab.backend.application.client.ClientResponse;
import com.quotelab.backend.application.client.ClientService;
import com.quotelab.backend.application.client.CreateClientRequest;
import com.quotelab.backend.application.client.UpdateClientRequest;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;


import jakarta.validation.Valid;

@SecurityRequirement(name = "bearerAuth")
@RestController
@RequestMapping("/api/v1/clients")
public class ClientController {
    private final ClientService clientService;

    public ClientController(ClientService clientService) {
        this.clientService = clientService;
    }

    @GetMapping
    public ResponseEntity<List<ClientResponse>> findAll() {
        return ResponseEntity.ok(clientService.findAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<ClientResponse> findById(@PathVariable UUID id) {
        return ResponseEntity.ok(clientService.findById(id));
    }

    @PostMapping
    public ResponseEntity<ClientResponse> create(@RequestBody @Valid CreateClientRequest request) {
        return ResponseEntity.status(201).body(clientService.createClient(request));
    }

    @PutMapping("/{id}")
    public ResponseEntity<ClientResponse> update(@PathVariable UUID id, @RequestBody @Valid UpdateClientRequest request) {
        return ResponseEntity.ok(clientService.updatedClient(id, request));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable UUID id) {
        clientService.delete(id);
        return ResponseEntity.noContent().build();
    }
}