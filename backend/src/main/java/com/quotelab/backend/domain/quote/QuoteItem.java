package com.quotelab.backend.domain.quote;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GenerationType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import java.math.BigDecimal;

import java.util.UUID;


@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "quote_items")

public class QuoteItem{
	@Id
	@GeneratedValue(strategy = GenerationType.UUID)
	private UUID id;
	
	@ManyToOne
	@JoinColumn(name = "quote_id", nullable = false)
	private Quote quote;

	@Column(nullable=false)
	private String description;

	@Column(nullable=false)
	private BigDecimal quantity;

	@Column(nullable=false)
	private BigDecimal unitPrice;

	@Column(nullable=false)
	private BigDecimal total;

	@Column(nullable = false)
	private Integer sortOrder = 0;
}