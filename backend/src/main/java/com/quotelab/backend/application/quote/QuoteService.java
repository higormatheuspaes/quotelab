package com.quotelab.backend.application.quote;

import java.math.BigDecimal;
import java.time.OffsetDateTime;
import java.util.UUID;
import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Service;

import com.quotelab.backend.domain.client.Client;
import com.quotelab.backend.domain.client.ClientRepository;
import com.quotelab.backend.domain.quote.Quote;
import com.quotelab.backend.domain.quote.QuoteRepository;
import com.quotelab.backend.domain.quote.QuoteStatus;
import com.quotelab.backend.domain.quote.QuoteItem;
import com.quotelab.backend.domain.user.User;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Service
public class QuoteService {
	private final QuoteRepository quoteRepository;
	private final ClientRepository clientRepository;

	public QuoteResponse createQuote(CreateQuoteRequest request, User user) {
		Client client = clientRepository.findById(request.getClientId())
			.orElseThrow(() -> new RuntimeException("Client not found"));

		if (!client.getUser().getId().equals(user.getId())){
			throw new RuntimeException("Access denied");
		}

		Quote quote = new Quote();
		quote.setUser(user);
		quote.setClient(client);
		quote.setTitle(request.getTitle());
		quote.setDescription(request.getDescription());
		quote.setNotes(request.getNotes());
		quote.setValidUntil(request.getValidUntil());
		quote.setDiscountType(request.getDiscountType());
		quote.setDiscountValue(request.getDiscountValue());
		quote.setStatus(QuoteStatus.DRAFT);
		quote.setVersion(1);
		quote.setPublicToken(UUID.randomUUID());
		quote.setCreatedAt(OffsetDateTime.now());
		quote.setUpdatedAt(OffsetDateTime.now());
		quote.setSubtotal(BigDecimal.ZERO);
		quote.setTotal(BigDecimal.ZERO);

		List<QuoteItem> items = new ArrayList<>();
		for (QuoteItemRequest quoteItem : request.getItems()){
			QuoteItem item = new QuoteItem();
			item.setDescription(quoteItem.getDescription());
			item.setQuantity(quoteItem.getQuantity());
			item.setUnitPrice(quoteItem.getUnitPrice());
			item.setTotal(quoteItem.getQuantity().multiply(quoteItem.getUnitPrice()));
			item.setQuote(quote);
			items.add(item);
		}
		
		BigDecimal sumTotal = items.stream()
			.map(QuoteItem::getTotal)
			.reduce(BigDecimal.ZERO, BigDecimal::add);
		
		BigDecimal total = sumTotal;
		if ((quote.getDiscountValue() != null) && (quote.getDiscountType() != null)){
			total = quote.getDiscountType().equals("PERCENT")
				? sumTotal.subtract(sumTotal.multiply(quote.getDiscountValue()).divide(BigDecimal.valueOf(100)))
				: sumTotal.subtract(quote.getDiscountValue());
		}

		quote.setTotal(total);
		quote.setItems(items);
		Quote saved = quoteRepository.save(quote);
		return toResponse(saved);
	}

	private QuoteResponse toResponse(Quote quote) {
		QuoteResponse quoteResponse = new QuoteResponse();
		quoteResponse.setId(quote.getId());
		quoteResponse.setClientId(quote.getClient().getId());
		quoteResponse.setClientName(quote.getClient().getName());
		quoteResponse.setTitle(quote.getTitle());
		quoteResponse.setDescription(quote.getDescription());
		quoteResponse.setStatus(quote.getStatus().name());
		quoteResponse.setVersion(quote.getVersion());
		quoteResponse.setSubTotal(quote.getSubtotal());
		quoteResponse.setDiscountType(quote.getDiscountType());
		quoteResponse.setDiscountValue(quote.getDiscountValue());
		quoteResponse.setTotal(quote.getTotal());
		quoteResponse.setValidUntil(quote.getValidUntil());
		quoteResponse.setNotes(quote.getNotes());
		quoteResponse.setPdfUrl(quote.getPdfUrl());
		quoteResponse.setCreatedAt(quote.getCreatedAt());
		quoteResponse.setUpdatedAt(quote.getUpdatedAt());
		quoteResponse.setItems(quote.getItems().stream()
			.map(item -> {
				QuoteItemResponse itemResponse = new QuoteItemResponse();
				itemResponse.setId(item.getId());
				itemResponse.setDescription(item.getDescription());
				itemResponse.setQuantity(item.getQuantity());
				itemResponse.setUnitPrice(item.getUnitPrice());
				itemResponse.setTotal(item.getTotal());
				itemResponse.setSortOrder(item.getSortOrder());
				return itemResponse;
			})
			.toList());

		return quoteResponse;
	}
}
