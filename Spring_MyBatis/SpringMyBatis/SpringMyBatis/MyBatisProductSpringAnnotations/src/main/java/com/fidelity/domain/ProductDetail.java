package com.fidelity.domain;

public class ProductDetail {

	private int productId;
	private String manufacturer;
	private String sku;
	private String upc;
	private int minimumAge;
	
	public ProductDetail(int productId, String manufacturer, String sku, String upc, int minimumAge) {
		super();
		this.productId = productId;
		this.manufacturer = manufacturer;
		this.sku = sku;
		this.upc = upc;
		this.minimumAge = minimumAge;
	}

	public ProductDetail() {
		
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((manufacturer == null) ? 0 : manufacturer.hashCode());
		result = prime * result + minimumAge;
		result = prime * result + productId;
		result = prime * result + ((sku == null) ? 0 : sku.hashCode());
		result = prime * result + ((upc == null) ? 0 : upc.hashCode());
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
		ProductDetail other = (ProductDetail) obj;
		if (manufacturer == null) {
			if (other.manufacturer != null)
				return false;
		} else if (!manufacturer.equals(other.manufacturer))
			return false;
		if (minimumAge != other.minimumAge)
			return false;
		if (productId != other.productId)
			return false;
		if (sku == null) {
			if (other.sku != null)
				return false;
		} else if (!sku.equals(other.sku))
			return false;
		if (upc == null) {
			if (other.upc != null)
				return false;
		} else if (!upc.equals(other.upc))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "ProductDetail [productId=" + productId + ", manufacturer=" + manufacturer + ", sku=" + sku + ", upc="
				+ upc + ", minimumAge=" + minimumAge + "]";
	}
}
