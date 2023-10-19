package com.fidelity.investmonkey.models;

import java.util.Objects;

public class Trade 
{    
    private String tradeId;
	private int quantity;
	private double executionPrice;
	private String direction;
	private Order order;
	private double cashValue;
	private String clientId;
	private String instrumentId;
	public Trade(String tradeId, int quantity, double executionPrice, String direction, Order order, double cashValue,
			String clientId, String instrumentId) {
		super();
		this.tradeId = tradeId;
		this.quantity = quantity;
		this.executionPrice = executionPrice;
		this.direction = direction;
		this.order = order;
		this.cashValue = cashValue;
		this.clientId = clientId;
		this.instrumentId = instrumentId;
	}
	public String getTradeId() {
		return tradeId;
	}
	public void setTradeId(String tradeId) {
		this.tradeId = tradeId;
	}
	public int getQuantity() {
		return quantity;
	}
	public void setQuantity(int quantity) {
		this.quantity = quantity;
	}
	public double getExecutionPrice() {
		return executionPrice;
	}
	public void setExecutionPrice(double executionPrice) {
		this.executionPrice = executionPrice;
	}
	public String getDirection() {
		return direction;
	}
	public void setDirection(String direction) {
		this.direction = direction;
	}
	public Order getOrder() {
		return order;
	}
	public void setOrder(Order order) {
		this.order = order;
	}
	public double getCashValue() {
		return cashValue;
	}
	public void setCashValue(double cashValue) {
		this.cashValue = cashValue;
	}
	public String getClientId() {
		return clientId;
	}
	public void setClientId(String clientId) {
		this.clientId = clientId;
	}
	public String getInstrumentId() {
		return instrumentId;
	}
	public void setInstrumentId(String instrumentId) {
		this.instrumentId = instrumentId;
	}
	@Override
	public int hashCode() {
		return Objects.hash(cashValue, clientId, direction, executionPrice, instrumentId, order, quantity, tradeId);
	}
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Trade other = (Trade) obj;
		return Double.doubleToLongBits(cashValue) == Double.doubleToLongBits(other.cashValue)
				&& Objects.equals(clientId, other.clientId) && Objects.equals(direction, other.direction)
				&& Double.doubleToLongBits(executionPrice) == Double.doubleToLongBits(other.executionPrice)
				&& Objects.equals(instrumentId, other.instrumentId) && Objects.equals(order, other.order)
				&& quantity == other.quantity && Objects.equals(tradeId, other.tradeId);
	}
	@Override
	public String toString() {
		return "Trade [tradeId=" + tradeId + ", quantity=" + quantity + ", executionPrice=" + executionPrice
				+ ", direction=" + direction + ", order=" + order + ", cashValue=" + cashValue + ", clientId="
				+ clientId + ", instrumentId=" + instrumentId + "]";
	}
	
	
}
