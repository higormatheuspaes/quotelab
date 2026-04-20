package com.quotelab.backend.application.client;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import com.quotelab.backend.domain.client.Client;
import com.quotelab.backend.domain.client.ClientRepository;
import com.quotelab.backend.domain.user.UserRepository;
import com.quotelab.backend.domain.user.User;

@Service
public class ClientService {
	private final ClientRepository clientRepository;
	private final UserRepository userRepository;

	public ClientService(ClientRepository clientRepository, UserRepository userRepository) {
		this.clientRepository = clientRepository;
		this.userRepository = userRepository;
	}

	private User getAuthenticatedUser() {
		String email = SecurityContextHolder.getContext().getAuthentication().getName();
		return userRepository.findByEmail(email)
			.orElseThrow(() -> new RuntimeException("User not found"));
	}

	public ClientResponse createClient(CreateClientRequest request){
		Client client = new Client();
		client.setName(request.getName());
		client.setEmail(request.getEmail());
		client.setCompany(request.getCompany());
		client.setDocument(request.getDocument());
		client.setNotes(request.getNotes());
		client.setPhone(request.getPhone());
		client.setUser(getAuthenticatedUser());
		clientRepository.save(client);

		ClientResponse response = new ClientResponse();
			response.setId(client.getId());
			response.setName(client.getName());
			response.setEmail(client.getEmail());
			response.setPhone(client.getPhone());
			response.setCompany(client.getCompany());
			response.setDocument(client.getDocument());
			response.setNotes(client.getNotes());
			response.setCreatedAt(client.getCreatedAt());
		return response;
	}

	public List<ClientResponse> findAll(){
		User user = getAuthenticatedUser();
		List<Client> clients = clientRepository.findByUser_Id(user.getId());

		List<ClientResponse> responses = new ArrayList<>();
		for (Client client : clients){
			ClientResponse response = new ClientResponse();
				response.setId(client.getId());
				response.setName(client.getName());
				response.setEmail(client.getEmail());
				response.setPhone(client.getPhone());
				response.setCompany(client.getCompany());
				response.setDocument(client.getDocument());
				response.setNotes(client.getNotes());
				response.setCreatedAt(client.getCreatedAt());

			responses.add(response);
		}
		return responses;

	}

	public ClientResponse findById(UUID id){
		Client client = clientRepository.findById(id)
			.orElseThrow(() -> new RuntimeException("Client not found"));

		User user = getAuthenticatedUser();
		if (!client.getUser().getId().equals(user.getId())) {
			throw new RuntimeException("Access denied");
		}

		ClientResponse response = new ClientResponse();
			response.setId(client.getId());
			response.setName(client.getName());
			response.setEmail(client.getEmail());
			response.setPhone(client.getPhone());
			response.setCompany(client.getCompany());
			response.setDocument(client.getDocument());
			response.setNotes(client.getNotes());
			response.setCreatedAt(client.getCreatedAt());
		return response;
	}

	public ClientResponse updatedClient(UUID id, UpdateClientRequest request){
		Client client = clientRepository.findById(id)
			.orElseThrow(() -> new RuntimeException("Client not found"));

		User user = getAuthenticatedUser();
		if (!client.getUser().getId().equals(user.getId())) {
			throw new RuntimeException("Access denied");
		}

		client.setName(request.getName());
		client.setEmail(request.getEmail());
		client.setPhone(request.getPhone());
		client.setCompany(request.getCompany());
		client.setDocument(request.getDocument());
		client.setNotes(request.getNotes());
		clientRepository.save(client); // faltou isso

		
		ClientResponse response = new ClientResponse();
			response.setId(client.getId());
			response.setName(client.getName());
			response.setEmail(client.getEmail());
			response.setPhone(client.getPhone());
			response.setCompany(client.getCompany());
			response.setDocument(client.getDocument());
			response.setNotes(client.getNotes());
			response.setCreatedAt(client.getCreatedAt());
		return response;
	}
	
	public void delete(UUID id) {
		Client client = clientRepository.findById(id)
			.orElseThrow(() -> new RuntimeException("Client not found"));

		User user = getAuthenticatedUser();
		if (!client.getUser().getId().equals(user.getId())) {
			throw new RuntimeException("Access denied");
		}

		clientRepository.delete(client);
	}

}
