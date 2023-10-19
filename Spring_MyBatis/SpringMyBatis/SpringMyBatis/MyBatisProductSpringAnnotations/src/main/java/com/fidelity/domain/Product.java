package com.fidelity.domain;

public class Product {

	private int productId;
	private int categoryId;
	private String name;
	private String description;
	private ProductDetail detail;
	private ProductType type;

	public Product(int productId, int categoryId, String name, String description, ProductDetail detail, ProductType type) {
		super();
		this.productId = productId;
		this.categoryId = categoryId;
		this.name = name;
		this.description = description;
		this.detail = detail;
		this.type = type;
	}
	
	public Product(int productId, int categoryId, String name, String description, ProductDetail detail) {
		this(productId, categoryId, name, description, detail, null);
	}
	

	public Product(int productId, int categoryId, String name, String description) {
		this(productId, categoryId, name, description, null, null);
	}
	
	public Product() {
		
	}

	// getters and setters
	public int getProductId() {
		return productId;
	}

	public void setProductId(int productId) {
		this.productId = productId;
	}

	public int getCategoryId() {
		return categoryId;
	}

	public void setCategoryId(int categoryId) {
		this.categoryId = categoryId;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public ProductDetail getDetail() {
		return detail;
	}

	public void setDetail(ProductDetail detail) {
		this.detail = detail;
	}
	
	public ProductType getType() {
		return type;
	}

	public void setType(ProductType type) {
		this.type = type;
	}
	
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + categoryId;
		result = prime * result + ((description == null) ? 0 : description.hashCode());
		result = prime * result + ((detail == null) ? 0 : detail.hashCode());
		result = prime * result + ((name == null) ? 0 : name.hashCode());
		result = prime * result + productId;
		result = prime * result + ((type == null) ? 0 : type.hashCode());
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
		if (categoryId != other.categoryId)
			return false;
		if (description == null) {
			if (other.description != null)
				return false;
		} else if (!description.equals(other.description))
			return false;
		if (detail == null) {
			if (other.detail != null)
				return false;
		} else if (!detail.equals(other.detail))
			return false;
		if (name == null) {
			if (other.name != null)
				return false;
		} else if (!name.equals(other.name))
			return false;
		if (productId != other.productId)
			return false;
		if (type != other.type)
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "Product [productId=" + productId + ", categoryId=" + categoryId + ", name=" + name + ", description="
				+ description + ", detail=" + detail + ", type=" + type + "]";
	}

}
