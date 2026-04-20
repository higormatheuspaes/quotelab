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
import jakarta.persistence.Enumerated;
import jakarta.persistence.EnumType;
import java.math.BigDecimal;
import java.time.OffsetDateTime;
import java.time.LocalDate;
import java.util.UUID;

import com.quotelab.backend.domain.client.Client;
import com.quotelab.backend.domain.user.User;


@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "quotes")

public class Quote {

	@Id
	@GeneratedValue(strategy = GenerationType.UUID)
	private UUID id;

	@ManyToOne
	@JoinColumn(name = "user_id", nullable = false)
	private User user;

	@ManyToOne
	@JoinColumn(name = "client_id", nullable = false)
	private Client client;

	private UUID publicToken;

	@Column(nullable = false)
	private String title;

	private String description;

	@Enumerated(EnumType.STRING)
	@Column(nullable = false)
	private QuoteStatus status;

	@Column(nullable = false)
	private Integer version;

	@Column(nullable = false)
	private BigDecimal subtotal;
	private String discountType;
	private BigDecimal discountValue;
	@Column(nullable = false)
	private BigDecimal total;
	@Column(nullable = false)
	private LocalDate validUntil;
	private String notes;
	private String pdfUrl;
	private String signedBy;
	private OffsetDateTime signedAt;
	private String signerIp;
}
