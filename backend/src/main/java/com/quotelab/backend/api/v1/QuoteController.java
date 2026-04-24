package com.quotelab.backend.api.v1;

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

import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import lombok.RequiredArgsConstructor;

@RestController
@SecurityRequirement(name = "bearerAuth")
@RequiredArgsConstructor
@RequestMapping("/api/v1/quotes")
public class QuoteController {
	private final QuoteService quoteService;

	@PostMapping
	public ResponseEntity<QuoteResponse> createQuote(@RequestBody CreateQuoteRequest request) {
		User user = (User) SecurityContextHolder.getContext()
			.getAuthentication()
			.getPrincipal();

		QuoteResponse response = quoteService.createQuote(request, user);
		return ResponseEntity.status(201).body(response);
	}
}
