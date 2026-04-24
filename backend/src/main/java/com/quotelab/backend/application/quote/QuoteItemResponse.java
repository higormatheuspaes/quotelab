package com.quotelab.backend.application.quote;

import java.math.BigDecimal;
import java.util.UUID;

import lombok.Data;

@Data
public class QuoteItemResponse {
	private UUID id;
	private String description;
	private BigDecimal quantity;
	private BigDecimal unitPrice;
	private BigDecimal total;
	private Integer sortOrder;
}
