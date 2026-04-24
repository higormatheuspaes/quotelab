package com.quotelab.backend.api.v1;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestBody;
import com.quotelab.backend.application.quote.CreateQuoteRequest;
import com.quotelab.backend.application.quote.QuoteResponse;
import com.quotelab.backend.application.quote.QuoteService;
import com.quotelab.backend.domain.user.User;

import lombok.RequiredArgsConstructor;

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
}
