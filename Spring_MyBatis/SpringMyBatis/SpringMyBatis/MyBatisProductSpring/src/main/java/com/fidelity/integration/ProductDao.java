package com.fidelity.integration;

import java.util.List;
import java.util.Map;

import com.fidelity.domain.Category;
import com.fidelity.domain.Product;

public interface ProductDao {
	List<Product> getProducts();
	Product getProduct(int productId);
	List<Product> getProductListByCategory(int categoryId);
	boolean insertProduct(Product product);
	boolean updateProduct(Product product);
	boolean deleteProduct(int productId);
	List<Product> getProductsWithDetail();
	List<Product> getProductsWithDetailExtension();
	List<Product> getProductsWithNestedDetail();
	List<Product> getProductsWithNestedDetailExtension();
	List<Product> getProductsWithNestedSelect();
	List<Category> getCategoriesWithNestedProduct();
	List<Category> getCategoriesWithNestedSelect();

	// Illustrating use of IDENTITY and SEQUENCE
	boolean insertProductWithIdentity(Product product);
	boolean insertProductWithSequence(Product product);
	List<Product> getProducts2();

	// Illustrating use of enum
	boolean insertProductWithTypeId(Product product);
	List<Product> getProductsWithTypeId();
	boolean insertProductWithTypeName(Product product);
	List<Product> getProductsWithTypeName();
	
	List<Product> getProductsByCategoryAndNameMap(int categoryId, String name);
	List<Product> getProductsByCategoryAndNameParam(int categoryId, String name);

	Map<Integer, Product> getProductsAsMap();
	
	
	List<Product> getProductsByBounds(int offset, int limit);
	Map<Integer, String> getProductIdNameMap();
	
	List<Product> getProductsByCategoryProcedure(int categoryId);
	List<Product> getProductsByCategoryFunction(int categoryId);
	
	
	void deleteProductsByCategoryProcedure(int categoryId);
}
