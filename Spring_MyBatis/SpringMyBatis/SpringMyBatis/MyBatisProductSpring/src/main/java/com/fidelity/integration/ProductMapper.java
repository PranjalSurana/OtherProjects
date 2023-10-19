package com.fidelity.integration;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.session.RowBounds;

import com.fidelity.domain.Category;
import com.fidelity.domain.Product;

public interface ProductMapper {
	List<Product> getProducts();
	Product getProduct(int productId);
	List<Product> getProductListByCategory(int categoryId);
	int insertProduct(Product product);
	List<Product> getProductsWithDetail();
	List<Product> getProductsWithDetailExtension();
	List<Product> getProductsWithNestedDetail();
	List<Product> getProductsWithNestedDetailExtension();
	List<Product> getProductsWithNestedSelect();
	List<Category> getCategoriesWithNestedProduct();
	List<Category> getCategoriesWithNestedSelect();
	int updateProduct(Product product);
	int deleteProduct(int productId);

	int insertProductWithIdentity(Product product);
	int insertProductWithSequence(Product product);
	List<Product> getProducts2();

	List<Product> getProductsWithTypeId();
	List<Product> getProductsWithTypeName();
	int insertProductWithTypeId(Product product);
	int insertProductWithTypeName(Product product);
	
	List<Product> getProductsByCategoryAndNameMap(Map<Object, Object> map);
	List<Product> getProductsByCategoryAndNameParam(int categoryId, String name);

	List<Product> getProducts(RowBounds bounds);

	/*
	 * These methods are declared as taking an int. This is OK because only HyperSQL
	 * allows us to use these methods like this and then there is a single parameter.
	 * When using in Oracle, we have to bypass the interface and use the SqlSession.
	 */
	List<Product> getProductsByCategoryProcedure(int categoryId);
	List<Product> getProductsByCategoryFunction(int categoryId);

	void deleteProductsByCategoryProcedure(int categoryId);
}
