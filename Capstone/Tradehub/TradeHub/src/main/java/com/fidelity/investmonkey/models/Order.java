package com.fidelity.investmonkey.models;

import java.util.Objects;

public class Order 
{
	private String InstrumentId;
	private int quantity;
	private double targetPrice;
	private String direction;
	private String clientId;
	private String orderId;
	
	public Order(String instrumentI, int quantity, double targetPrice, String direction, String clientId,
			String orderId) {
		super();
		InstrumentId = instrumentI;
		this.quantity = quantity;
		this.targetPrice = targetPrice;
		this.direction = direction;
		this.clientId = clientId;
		this.orderId = orderId;
	}
	public String getInstrumentId() {
		return InstrumentId;
	}
	public void setInstrumentI(String instrumentI) {
		InstrumentId = instrumentI;
	}
	public int getQuantity() {
		return quantity;
	}
	public void setQuantity(int quantity) {
		this.quantity = quantity;
	}
	public double getTargetPrice() {
		return targetPrice;
	}
	public void setTargetPrice(double targetPrice) {
		this.targetPrice = targetPrice;
	}
	public String getDirection() {
		return direction;
	}
	public void setDirection(String direction) {
		this.direction = direction;
	}
	public String getClientId() {
		return clientId;
	}
	public void setClientId(String clientId) {
		this.clientId = clientId;
	}
	public String getOrderId() {
		return orderId;
	}
	public void setOrderId(String orderId) {
		this.orderId = orderId;
	}
	@Override
	public int hashCode() {
		return Objects.hash(InstrumentId, clientId, direction, orderId, quantity, targetPrice);
	}
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Order other = (Order) obj;
		return Objects.equals(InstrumentId, other.InstrumentId) && Objects.equals(clientId, other.clientId)
				&& Objects.equals(direction, other.direction) && Objects.equals(orderId, other.orderId)
				&& quantity == other.quantity
				&& Double.doubleToLongBits(targetPrice) == Double.doubleToLongBits(other.targetPrice);
	}
	@Override
	public String toString() {
		return "Order [InstrumentI=" + InstrumentId + ", quantity=" + quantity + ", targetPrice=" + targetPrice
				+ ", direction=" + direction + ", clientId=" + clientId + ", orderId=" + orderId + "]";
	}
	
	
}
