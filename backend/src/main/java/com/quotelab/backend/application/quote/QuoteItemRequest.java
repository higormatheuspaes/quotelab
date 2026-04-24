package com.quotelab.backend.application.quote;

import lombok.Data;


import java.math.BigDecimal;

@Data
public class QuoteItemRequest {
	private String description;
	private BigDecimal quantity;
	private BigDecimal unitPrice;
	private Integer sortOrder;
}


