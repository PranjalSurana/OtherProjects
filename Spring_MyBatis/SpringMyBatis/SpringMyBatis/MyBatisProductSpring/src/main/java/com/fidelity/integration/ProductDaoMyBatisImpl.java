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
	private static final Logger LOGGER = LoggerFactory.getLogger(ProductDaoMyBatisImpl.class);

	@Autowired
	private ProductMapper mapper;

	@Override
	public List<Product> getProducts() {
		LOGGER.info("getProducts");
		return mapper.getProducts();
	}

	@Override
	public Product getProduct(int productId) {
		LOGGER.info("getProduct");
		return mapper.getProduct(productId);
	}

	@Override
	public List<Product> getProductListByCategory(int categoryId) {
		LOGGER.info("getProductListByCategory");
		return mapper.getProductListByCategory(categoryId);

	}

	@Override
	public List<Product> getProductsWithDetail() {
		LOGGER.info("getProductsWithDetail");
		return mapper.getProductsWithDetail();
	}

	@Override
	public List<Product> getProductsWithDetailExtension() {
		LOGGER.info("getProductsWithDetailExtension");
		return mapper.getProductsWithDetailExtension();
	}

	@Override
	public List<Product> getProductsWithNestedDetail() {
		LOGGER.info("getProductWithNestedDetail");
		return mapper.getProductsWithNestedDetail();
	}

	@Override
	public List<Product> getProductsWithNestedDetailExtension() {
		LOGGER.info("getProductsWithNestedDetailExtension");
		return mapper.getProductsWithNestedDetailExtension();
	}

	@Override
	public List<Product> getProductsWithNestedSelect() {
		LOGGER.info("getProductsWithNestedSelect");
		return mapper.getProductsWithNestedSelect();
	}

	@Override
	public List<Category> getCategoriesWithNestedProduct() {
		LOGGER.info("getCategoriesWithNestedProduct");
		return mapper.getCategoriesWithNestedProduct();
	}

	@Override
	public List<Category> getCategoriesWithNestedSelect() {
		LOGGER.info("getCategoriesWithNestedSelect");
		return mapper.getCategoriesWithNestedSelect();
	}

	/*
	 * In this case, update, insert & delete should only affect one row
	 * because they are accessed by primary key: this will not always be
	 * the case (it may be more appropriate to return the number of rows)
	 */
	@Override
	@Transactional
	public boolean insertProduct(Product product) {
		LOGGER.info("insertProduct");
		return mapper.insertProduct(product) == 1;
	}

	@Override
	@Transactional
	public boolean updateProduct(Product product) {
		LOGGER.info("updateProduct");
		return mapper.updateProduct(product) == 1;
	}

	@Override
	@Transactional
	public boolean deleteProduct(int productId) {
		LOGGER.info("deleteProduct");
		return mapper.deleteProduct(productId) == 1;
	}

	@Override
	@Transactional
	public boolean insertProductWithIdentity(Product product) {
		LOGGER.info("insertProductWithIdentity");
		return mapper.insertProductWithIdentity(product) == 1;
	}

	@Override
	@Transactional
	public boolean insertProductWithSequence(Product product) {
		LOGGER.info("insertProductWithSequence");
		return mapper.insertProductWithIdentity(product) == 1;
	}

	@Override
	public List<Product> getProducts2() {
		LOGGER.info("getProducts2");
		return mapper.getProducts2();
	}

	@Override
	@Transactional
	public boolean insertProductWithTypeId(Product product) {
		LOGGER.info("insertProductWithTypeId");
		return mapper.insertProductWithTypeId(product) == 1;
	}

	@Override
	public List<Product> getProductsWithTypeId() {
		LOGGER.info("getProductsWithTypeId");
		return mapper.getProductsWithTypeId();
	}

	@Override
	@Transactional
	public boolean insertProductWithTypeName(Product product) {
		LOGGER.info("insertProductWithTypeName");
		return mapper.insertProductWithTypeName(product) == 1;
	}

	@Override
	public List<Product> getProductsWithTypeName() {
		LOGGER.info("getProductsWithTypeName");
		return mapper.getProductsWithTypeName();
	}

	// Finally, a reason for having the DAO!
	@Override
	public List<Product> getProductsByCategoryAndNameMap(int categoryId, String name) {
		LOGGER.info("getProductsByCategoryAndNameMap");
		Map<Object, Object> parameterMap = new HashMap<>(2);
		parameterMap.put("categoryId", categoryId);
		parameterMap.put("name", name + "%");
		return mapper.getProductsByCategoryAndNameMap(parameterMap);
	}

	@Override
	public List<Product> getProductsByCategoryAndNameParam(int categoryId, String name) {
		LOGGER.info("getProductsByCategoryAndNameParam");

		return mapper.getProductsByCategoryAndNameParam(categoryId, name);
	}

	// Not normally required. Only needed here to get access to SqlSession methods directly
	@Autowired
	SqlSession	session;
	
	@Override
	public Map<Integer, Product> getProductsAsMap() {
		LOGGER.info("getProductsAsMap");
		Map<Integer, Product> products = session.selectMap(
				"com.fidelity.integration.ProductMapper.getProducts", // query
				"productId");                                         // key value
		return products;
	}
	
	@Override
	public List<Product> getProductsByBounds(int offset, int limit) {
		LOGGER.info("getProductsByBounds");
		RowBounds bounds = new RowBounds(offset, limit);
		List<Product> products = mapper.getProducts(bounds);

		return products;
	}
	
	@Override
	public Map<Integer, String> getProductIdNameMap() {
		LOGGER.info("getProductIdNameMap");
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

	/*
	 * Getting results from an Oracle stored procedure is complicated (see below)
	 * but a HyperSQL stored procedure behaves like every other statement.
	 * 
	 * Normally, we would use some sort of Factory pattern to allow us to choose
	 * the appropriate code, but here, for testing purposes, we just comment out 
	 * the one we are not using.
	 */
	@Override
	public List<Product> getProductsByCategoryProcedure(int categoryId) {
		LOGGER.info("getProductsByCategoryProcedure");
//		List<Product> products = getProductsByCategoryProcedureOracle(categoryId);
		List<Product> products = mapper.getProductsByCategoryProcedure(categoryId);
		return products;
	}
	
	@Override
	public List<Product> getProductsByCategoryFunction(int categoryId) {
		LOGGER.info("getProductsByCategoryFunction");
//		List<Product> products = getProductsByCategoryFunctionOracle(categoryId);
		List<Product> products = mapper.getProductsByCategoryFunction(categoryId);
		return products;
	}

	/*
	 * The only way to get a results set back from an Oracle stored procedure is through an OUT parameter
	 * of type SYS_REFCURSOR. This requires us to use a Map to access the parameters. The results have been
	 * correctly decoded by MyBatis, which applied a ResultMap.
	 */
	@SuppressWarnings("unused")
	private List<Product> getProductsByCategoryProcedureOracle(int categoryId) {
		LOGGER.info("getProductsByCategoryProcedureOracle");
		return getProductsByCategoryCallableOracle(categoryId, 
				"com.fidelity.integration.ProductMapper.getProductsByCategoryProcedure");
	}

	/*
	 * Even though an Oracle function can RETURN a SYS_REFCURSOR, the driver forces us to use a particular
	 * JDBC call format that means it must be returned from MyBatis in a parameter map, just like a proc!
	 */
	public List<Product> getProductsByCategoryFunctionOracle(int categoryId) {
		LOGGER.info("getProductsByCategoryFunctionOracle");
		return getProductsByCategoryCallableOracle(categoryId, 
				"com.fidelity.integration.ProductMapper.getProductsByCategoryFunction");
	}

	@SuppressWarnings("unchecked")
	private List<Product> getProductsByCategoryCallableOracle(int categoryId, String query) {
		Map<Object, Object> parameterMap = new HashMap<>(2);
		parameterMap.put("categoryId", categoryId);
		parameterMap.put("results", null);
		session.selectList(
				query,			// query
				parameterMap);  // map of parameters
		return (List<Product>) parameterMap.get("results");
	}

	@Transactional
	@Override
	public void deleteProductsByCategoryProcedure(int categoryId) {
		LOGGER.info("deleteProductsByCategoryProcedure");
		mapper.deleteProductsByCategoryProcedure(categoryId);
	}
}
