package com.fidelity.investmonkey.models;

public class Holdings 
{    
     private String instrumentId;
	 private String instrumentDescription;
	 private String categoryId;
	 private double askNumber;
	 private int quantity;
	 private double totalPrice;
	public Holdings(String instrumentId, String instrumentDescription, String categoryId, double askNumber,
			int quantity, double totalPrice) {
		super();
		this.instrumentId = instrumentId;
		this.instrumentDescription = instrumentDescription;
		this.categoryId = categoryId;
		this.askNumber = askNumber;
		this.quantity = quantity;
		this.totalPrice = totalPrice;
	}
	public String getInstrumentId() {
		return instrumentId;
	}
	public void setInstrumentId(String instrumentId) {
		this.instrumentId = instrumentId;
	}
	public String getInstrumentDescription() {
		return instrumentDescription;
	}
	public void setInstrumentDescription(String instrumentDescription) {
		this.instrumentDescription = instrumentDescription;
	}
	public String getCategoryId() {
		return categoryId;
	}
	public void setCategoryId(String categoryId) {
		this.categoryId = categoryId;
	}
	public double getAskNumber() {
		return askNumber;
	}
	public void setAskNumber(double askNumber) {
		this.askNumber = askNumber;
	}
	public int getQuantity() {
		return quantity;
	}
	public void setQuantity(int quantity) {
		this.quantity = quantity;
	}
	public double getTotalPrice() {
		return totalPrice;
	}
	public void setTotalPrice(double totalPrice) {
		this.totalPrice = totalPrice;
	}
	@Override
	public String toString() {
		return "Holdings [instrumentId=" + instrumentId + ", instrumentDescription=" + instrumentDescription
				+ ", categoryId=" + categoryId + ", askNumber=" + askNumber + ", quantity=" + quantity + ", totalPrice="
				+ totalPrice + "]";
	}
	 
	 
	 
}
