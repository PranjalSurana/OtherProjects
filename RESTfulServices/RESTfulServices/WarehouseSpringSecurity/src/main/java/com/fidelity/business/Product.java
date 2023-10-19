package com.fidelity.business;

import java.math.BigDecimal;



public abstract class Product {
	private String description;
	private int id;
	private BigDecimal price;

	public void setPrice(BigDecimal unitPrice) {
		this.price = unitPrice.setScale(2, BigDecimal.ROUND_HALF_UP);
	}

	// Eclipse-generated from here
	public Product(String description, int id, BigDecimal unitPrice) {
		this.description = description;
		this.id = id;
		setPrice(unitPrice);
	}
	
	public Product() { }

	public String getDescription() {
		return description;
	}

	public int getId() {
		return id;
	}

	public BigDecimal getPrice() {
		return price;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public void setId(int id) {
		this.id = id;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((description == null) ? 0 : description.hashCode());
		result = prime * result + id;
		result = prime * result + ((price == null) ? 0 : price.hashCode());
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
		Product other = (Product) obj;
		if (description == null) {
			if (other.description != null)
				return false;
		} else if (!description.equals(other.description))
			return false;
		if (id != other.id)
			return false;
		if (price == null) {
			if (other.price != null)
				return false;
		} else if (!price.equals(other.price))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "Product [description=" + description + ", id=" + id + ", price=" + price + "]";
	}

}
