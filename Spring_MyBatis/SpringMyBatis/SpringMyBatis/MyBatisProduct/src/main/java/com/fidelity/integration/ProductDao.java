package com.fidelity.integration;

import java.util.List;

import com.fidelity.domain.Product;

public interface ProductDao {
	List<Product> getProducts();
	Product getProduct(int productId);
	List<Product> getProductListByCategory(int categoryId);
	void insertProduct(Product product);
}
