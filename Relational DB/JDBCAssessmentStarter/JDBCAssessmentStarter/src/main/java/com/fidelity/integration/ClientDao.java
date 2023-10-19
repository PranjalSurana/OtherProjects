package com.fidelity.integration;

import java.util.List;

import com.fidelity.model.Client;

public interface ClientDao {
	// you may not change any of these methods
	List<Client> getClients();
	List<Client> getClientById(int clientId);
	void insertClient(Client client);
	void deleteClient(int clientId);
}
