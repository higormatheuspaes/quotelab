package com.quoutelab.backend.domain.quote;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;

public interface QuoteRepository extends JpaRepository<Quote, UUID>{
	List<Quote> findByUser_Id (UUID userId);

	Optional<Quote> findByPublicToken(UUID token);

	List<Quote> findByUser_IdAndStatus(UUID userid, QuoteStatus status);

	boolean existsByIdAndUser_Id(UUID quoteId, UUID userId);
}
