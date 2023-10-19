package com.fidelity.integration;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.ibatis.session.ResultContext;
import org.apache.ibatis.session.ResultHandler;
import org.apache.ibatis.session.RowBounds;
import org.apache.ibatis.session.SqlSession;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.fidelity.domain.Category;
import com.fidelity.domain.Product;

@Repository("productDaoImpl")
@Primary
public class ProductDaoMyBatisImpl implements ProductDao {
	private static final Logger logger = LoggerFactory.getLogger(ProductDaoMyBatisImpl.class);

	@Autowired
	private ProductMapper mapper;

	@Override
	@Transactional
	public boolean insertProduct(Product product) {
		return mapper.insertProduct(product) == 1;
	}

	/*
	 * In this case, both update and insert should only affect one row
	 * because they are accessed by primary key: this will not always be
	 * the case (it may be more appropriate to return the number of rows)
	 */
	@Override
	@Transactional
	public boolean updateProduct(Product product) {
		return mapper.updateProduct(product) == 1;
	}

	@Override
	@Transactional
	public boolean deleteProduct(int productId) {
		return mapper.deleteProduct(productId) == 1;
	}

	@Override
	public List<Product> getProducts() {
		logger.info("getProducts");
		return mapper.getProducts();
	}

	@Override
	public Product getProduct(int productId) {
		return mapper.getProduct(productId);
	}

	@Override
	public List<Product> getProductListByCategory(int categoryId) {
		return mapper.getProductListByCategory(categoryId);

	}

	@Override
	public List<Product> getProductsWithDetail() {
		logger.info("getProductWithDetail");
		return mapper.getProductsWithDetail();
	}

	@Override
	public List<Product> getProductsWithNestedDetail() {
		logger.info("getProductWithNestedDetail");
		return mapper.getProductsWithNestedDetail();
	}

	@Override
	public List<Product> getProductsWithNestedSelect() {
		logger.info("getProductWithNestedSelect");
		return mapper.getProductsWithNestedSelect();
	}

	@Override
	public List<Category> getCategoriesWithNestedProduct() {
		logger.info("getProductWithNestedSelect");
		return mapper.getCategoriesWithNestedProduct();
	}

	@Override
	public List<Category> getCategoriesWithNestedSelect() {
		logger.info("getProductWithNestedSelect");
		return mapper.getCategoriesWithNestedSelect();
	}

	@Override
	@Transactional
	public boolean insertProductWithIdentity(Product product) {
		return mapper.insertProductWithIdentity(product) == 1;
	}

	@Override
	@Transactional
	public boolean insertProductWithSequence(Product product) {
		return mapper.insertProductWithIdentity(product) == 1;
	}

	@Override
	public List<Product> getProducts2() {
		logger.info("getProducts2");
		return mapper.getProducts2();
	}

	@Override
	@Transactional
	public boolean insertProductWithTypeId(Product product) {
		return mapper.insertProductWithTypeId(product) == 1;
	}

	@Override
	public List<Product> getProductsWithTypeId() {
		logger.info("getProductsWithTypeId");
		return mapper.getProductsWithTypeId();
	}

	@Override
	@Transactional
	public boolean insertProductWithTypeName(Product product) {
		return mapper.insertProductWithTypeName(product) == 1;
	}

	@Override
	public List<Product> getProductsWithTypeName() {
		logger.info("getProductsWithTypeName");
		return mapper.getProductsWithTypeName();
	}

	// Finally, a reason for having the DAO!
	@Override
	public List<Product> getProductsByCategoryAndNameMap(int categoryId, String name) {
		logger.info("getProductsByCategoryAndNameMap");
		Map<Object, Object> parameterMap = new HashMap<>(2);
		parameterMap.put("categoryId", categoryId);
		parameterMap.put("name", name + "%");
		return mapper.getProductsByCategoryAndNameMap(parameterMap);
	}

	@Override
	public List<Product> getProductsByCategoryAndNameParam(int categoryId, String name) {
		logger.info("getProductsByCategoryAndNameParam");

		return mapper.getProductsByCategoryAndNameParam(categoryId, name);
	}

	// Not normally required. Only needed here to get access to SqlSession methods directly
	@Autowired
	SqlSession	session;
	
	@Override
	public Map<Integer, Product> getProductsAsMap() {
		logger.info("getProductsAsMap");
		return mapper.getProductsAsMap();
	}

	@Override
	public List<Product> getProductsByBounds(int offset, int limit) {
		logger.info("getProductsByBounds");
		RowBounds bounds = new RowBounds(offset, limit);
		List<Product> products = mapper.getProducts(bounds);

		return products;
	}
	
	@Override
	public Map<Integer, String> getProductIdNameMap() {
		Map<Integer, String> map = new HashMap<>();
		session.select("com.fidelity.integration.ProductMapper.getProducts", // query
				new ResultHandler<Product>() {
					@Override
					public void handleResult(ResultContext<? extends Product> context) {
						Product product = context.getResultObject();
						map.put(product.getProductId(), product.getName());
					}
				});
		return map;
	}

}
