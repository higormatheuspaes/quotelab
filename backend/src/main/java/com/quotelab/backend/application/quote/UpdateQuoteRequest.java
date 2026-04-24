package com.quotelab.backend.application.quote;
import java.math.BigDecimal;
import java.time.LocalDate;

import lombok.Data;

import java.util.List;

@Data
public class UpdateQuoteRequest {
	private String title;
	private String description;
	private String discountType;
	private BigDecimal discountValue;
	private LocalDate validUntil;
	private String notes;
	private List<QuoteItemRequest> items;
}
