package com.fidelity.investmonkey.models;

import java.util.Objects;

public class Balance 
{
	private String clientId;
	private double balance;
	
	public Balance(String clientId) {
		super();
		this.clientId = clientId;
		this.balance = 1000000000;
	}

	public String getClientId() {
		return clientId;
	}

	public void setClientId(String clientId) {
		this.clientId = clientId;
	}

	public double getBalance() {
		return balance;
	}

	public void setBalance(double balance) {
		this.balance = balance;
	}

	@Override
	public String toString() {
		return "Balance [clientId=" + clientId + ", balance=" + balance + "]";
	}

	@Override
	public int hashCode() {
		return Objects.hash(balance, clientId);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Balance other = (Balance) obj;
		return Double.doubleToLongBits(balance) == Double.doubleToLongBits(other.balance)
				&& Objects.equals(clientId, other.clientId);
	}
	
	
	
	
}
