package com.fidelity.investmonkey.service;
import java.util.ArrayList;
import java.util.List;

import com.fidelity.investmonkey.models.Client;
import com.fidelity.investmonkey.exception.*;
public class ClientService {
	private List<Client> clients=new ArrayList<Client>();
	public void addNewClient(Client client) {
		if(client==null) {
			throw new NullPointerException("Client to be added cannot be null");
		}
		for(Client currentClient:this.clients) {
			if(currentClient.getClientId()==client.getClientId() || currentClient.getEmail()==client.getEmail()) {
				throw new InvalidClientException("Client Already Exists");
			}
		}
		clients.add(client);	
	}
	public void removeClient(Client client) {
		if(client==null) {
			throw new NullPointerException("Client to be added cannot be null");
		}
		for(Client currentClient:this.clients) {
			if(currentClient.getClientId()==client.getClientId()) {
				this.clients.remove(client);
				return;
			}
		}
		throw new InvalidClientException("Client does not exist");
	}
	public List<Client> getClients(){
		return this.clients;
	}
	public int numberOfClients() {
		return this.clients.size();
	}
	public Client getClientById(String clientId) {
		for(Client currentClient:this.clients) {
			if(currentClient.getClientId()==clientId) {
				return currentClient;
			}
		}
		return null;
	}
	public boolean loginClient(String email,String password){
		for(Client client:this.clients) {
			if(client.getEmail()==email) {
				if(client.getPassword()==password) {
					return true;
				}
				else {
					throw new IllegalArgumentException("Incorrect Password");
				}
			}
			
		}
		throw new InvalidClientException("Client Does not Exist");
	}
	
	
	
}
