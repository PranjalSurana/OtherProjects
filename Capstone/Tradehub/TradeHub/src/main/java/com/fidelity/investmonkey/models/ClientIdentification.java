package com.fidelity.investmonkey.models;

import com.fidelity.investmonkey.exception.*;
public class ClientIdentification {
	private String clientIdentificationType;
	private String clientIdentificationValue;
	public ClientIdentification(String clientIdentificationType, String clientIdentificationValue) {
		super();
		if(clientIdentificationType!="Aadhar" && clientIdentificationType!="SSN" ) {
			throw new InvalidIdentificationType("Identification type should be aadhar or SSN");
		}
		if(clientIdentificationValue==null) {
			throw new NullPointerException("Client Idnetification Value Cannot be null");
		}
		this.clientIdentificationType = clientIdentificationType;
		this.clientIdentificationValue = clientIdentificationValue;
	}
	
}

