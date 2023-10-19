package com.fidelity.domain;

import java.time.LocalTime;
import java.util.UUID;

public class MessageServiceResponse {
	private String message;
	private LocalTime responseTime;
	private String transactionId;
	
	public MessageServiceResponse() {}
	
	public MessageServiceResponse(String message) {
		this(message, LocalTime.now(), UUID.randomUUID().toString());
	}

	public MessageServiceResponse(String message, LocalTime responseTime, String transactionId) {
		super();
		this.message = message;
		this.responseTime = responseTime;
		this.transactionId = transactionId;
	}
	
	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public LocalTime getResponseTime() {
		return responseTime;
	}

	public void setResponseTime(LocalTime responseTime) {
		this.responseTime = responseTime;
	}

	public String getTransactionId() {
		return transactionId;
	}

	public void setTransactionId(String transactionId) {
		this.transactionId = transactionId;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((message == null) ? 0 : message.hashCode());
		result = prime * result + ((responseTime == null) ? 0 : responseTime.hashCode());
		result = prime * result + ((transactionId == null) ? 0 : transactionId.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		MessageServiceResponse other = (MessageServiceResponse) obj;
		if (message == null) {
			if (other.message != null)
				return false;
		} else if (!message.equals(other.message))
			return false;
		if (responseTime == null) {
			if (other.responseTime != null)
				return false;
		} else if (!responseTime.equals(other.responseTime))
			return false;
		if (transactionId == null) {
			if (other.transactionId != null)
				return false;
		} else if (!transactionId.equals(other.transactionId))
			return false;
		return true;
	}
	
	
}
