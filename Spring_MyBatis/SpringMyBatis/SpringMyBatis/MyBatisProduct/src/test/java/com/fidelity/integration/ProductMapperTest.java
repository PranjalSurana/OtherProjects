package com.fidelity.integration;

import static org.junit.jupiter.api.Assertions.*;

import java.io.InputStream;
import java.sql.Connection;
import java.util.List;

import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import com.fidelity.domain.Product;

public class ProductMapperTest {
	static String configFile = "mybatis-config.xml";
	static SqlSessionFactory sqlSessionFactory;

	private Product product1;

	@BeforeAll
	static void setUpClass() throws Exception {
		InputStream is = Resources.getResourceAsStream(configFile);
		sqlSessionFactory = new SqlSessionFactoryBuilder().build(is);
	}
	
	@BeforeEach
	void setUp() {
		product1 = new Product(1, 1, "Widget", "This widget is indispensible");
	}

	@Test
	void testOpenConnection() {
		try (SqlSession session = sqlSessionFactory.openSession()) {
			Connection conn = session.getConnection();
			assertNotNull(conn);
		}
	}

	@Test
	void testGetProducts() {
		try (SqlSession session = sqlSessionFactory.openSession()) {
			ProductMapper mapper = session.getMapper(ProductMapper.class);
			
			List<Product> products = mapper.getProducts();
			
			assertNotNull(products);
			assertEquals(2, products.size());
			for (Product product : products) {
				assertNotNull(product);
			}
			assertTrue(products.contains(product1));
		}
	}
	
	@Test
	void testGetProduct() {
		try (SqlSession session = sqlSessionFactory.openSession()) {
			ProductMapper mapper = session.getMapper(ProductMapper.class);
			
			Product product = mapper.getProduct(1);
			
			assertNotNull(product);
			assertEquals(product1, product);
		}
	}
	
	@Test
	void testGetProductsByCategory() {
		try (SqlSession session = sqlSessionFactory.openSession()) {
			ProductMapper mapper = session.getMapper(ProductMapper.class);
			
			List<Product> products = mapper.getProductListByCategory(1);
			
			assertNotNull(products);
			assertEquals(2, products.size());
			for (Product product : products) {
				assertNotNull(product);
			}
			assertTrue(products.contains(product1));
		}
	}
	
}
