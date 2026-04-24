package com.quotelab.backend.application.quote;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.OffsetDateTime;
import java.util.UUID;

import lombok.Data;

import java.util.List;

@Data
public class QuoteResponse {
	private UUID id;
	private UUID clientId;
	private String clientName;
	private String title;
	private String description;
	private String status;
	private Integer version;
	private BigDecimal subTotal;
	private String discountType;
	private BigDecimal discountValue;
	private BigDecimal total;
	private LocalDate validUntil;
	private String notes;
	private String pdfUrl;
	private OffsetDateTime createdAt;
	private OffsetDateTime updatedAt;
	private List<QuoteItemResponse> items;
}
