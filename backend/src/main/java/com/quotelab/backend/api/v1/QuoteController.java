package com.quotelab.backend.api.v1;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;

import org.apache.catalina.connector.Response;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestBody;
import com.quotelab.backend.application.quote.CreateQuoteRequest;
import com.quotelab.backend.application.quote.QuoteResponse;
import com.quotelab.backend.application.quote.QuoteService;
import com.quotelab.backend.application.quote.UpdateQuoteRequest;
import com.quotelab.backend.domain.user.User;

import lombok.RequiredArgsConstructor;
import java.util.List;
import java.util.UUID;

@Tag(name = "Orçamentos", description = "Criação e gestão de orçamentos")
@SecurityRequirement(name = "bearerAuth")
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/quotes")
public class QuoteController {
	private final QuoteService quoteService;

	@Operation(summary = "Criar orçamento (POST /api/v1/quotes)")
	@PostMapping
	public ResponseEntity<QuoteResponse> createQuote(@RequestBody CreateQuoteRequest request) {
		User user = (User) SecurityContextHolder.getContext()
			.getAuthentication()
			.getPrincipal();

		QuoteResponse response = quoteService.createQuote(request, user);
		return ResponseEntity.status(201).body(response);
	}

	@Operation(summary = "Retorna os orçamentos do freelancer logado (GET /api/v1/quotes)")
	@GetMapping
	public ResponseEntity<List<QuoteResponse>> findAll() {
		User user = (User) SecurityContextHolder.getContext()
			.getAuthentication()
			.getPrincipal();

		return ResponseEntity.ok(quoteService.findAll(user));
	}

	@Operation(summary = "Busca um orçamento pelo seu respectivo id (GET /api/v1/quotes/{id})")
	@GetMapping("/{id}")
	public ResponseEntity<QuoteResponse> findById(@PathVariable UUID id) {
		User user = (User) SecurityContextHolder.getContext()
			.getAuthentication()
			.getPrincipal();

		return ResponseEntity.ok(quoteService.findById(id, user));
	}

	@Operation(summary = "Atualizar orçamento (PUT /api/v1/quotes/{id})")
	@PutMapping("/{id}")
	public ResponseEntity<QuoteResponse> update(@PathVariable UUID id, @RequestBody UpdateQuoteRequest request) {
		User user = (User) SecurityContextHolder.getContext()
			.getAuthentication()
			.getPrincipal();

		return ResponseEntity.ok(quoteService.updateQuote(id, request, user));

	}

	@Operation(summary =  "Envia para o cliente atualizando o estado (POST /api/v1/quotes/{id}/send)")
	@PostMapping("/{id}/send")
	public ResponseEntity<QuoteResponse> send (@PathVariable UUID id) {
		User user = (User) SecurityContextHolder.getContext()
			.getAuthentication()
			.getPrincipal();

		return ResponseEntity.ok(quoteService.sendQuote(id, user));
	}

	@Operation(summary = "Cria um rascunho do orçamento pelo seu id (POST /api/v1/quotes/{id}/duplicate)")
	@PostMapping("/{id}/duplicate")
	public ResponseEntity<QuoteResponse> duplicate (@PathVariable UUID id) {
		User user = (User) SecurityContextHolder.getContext()
			.getAuthentication()
			.getPrincipal();

		return ResponseEntity.ok(quoteService.duplicateQuote(id, user));
	}

}
