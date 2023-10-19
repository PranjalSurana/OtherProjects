package com.fidelity.model;

public class Client {
	// you may not change any of these fields
	private int clientId;
	private String clientName;
	private ClientRisk clientRisk;
	private String workPhone;
	
	// Eclipse-generated from here

	public Client(int clientId, String clientName, 
			      ClientRisk clientRisk, String workPhone) {
		super();
		this.clientId = clientId;
		this.clientName = clientName;
		this.clientRisk = clientRisk;
		this.workPhone = workPhone;
	}

	public int getClientId() {
		return clientId;
	}

	public String getClientName() {
		return clientName;
	}

	public ClientRisk getClientRisk() {
		return clientRisk;
	}

	public String getWorkPhone() {
		return workPhone;
	}
}