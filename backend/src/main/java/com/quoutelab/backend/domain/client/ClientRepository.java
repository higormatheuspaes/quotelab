package com.quoutelab.backend.domain.client;

import java.util.UUID;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;


public interface ClientRepository extends JpaRepository<Client, UUID>{
	List<Client> findByUser_Id(UUID userId);
	boolean existsByIdAndUser_Id(UUID clientId, UUID userId);
}

