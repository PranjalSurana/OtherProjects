package com.fidelity.integration;

import java.util.List;

import org.apache.ibatis.session.SqlSession;

import com.fidelity.domain.Product;

public class ProductDaoImpl implements ProductDao {

	@Override
	public void insertProduct(Product product) {
		// This is what it looks like without using a try-with-resources block
		SqlSession session = MyBatisSqlSessionFactory.openSession();
		try {
			ProductMapper productMapper = session.getMapper(ProductMapper.class);
			productMapper.insertProduct(product);
			session.commit();
		} finally {
			// close session, return db connection to pool
			session.close();
		}
	}

	@Override
	public List<Product> getProducts() {
		List<Product> products = null;
		try (SqlSession session = MyBatisSqlSessionFactory.openSession()) {
			ProductMapper productMapper = session.getMapper(ProductMapper.class);
			products = productMapper.getProducts();
		}
		return products;
	}

	@Override
	public Product getProduct(int productId) {
		Product product = null;
		try (SqlSession session = MyBatisSqlSessionFactory.openSession()) {
			ProductMapper productMapper = session.getMapper(ProductMapper.class);
			product = productMapper.getProduct(productId);
		}
		return product;
	}

	@Override
	public List<Product> getProductListByCategory(int categoryId) {
		List<Product> products = null;
		try (SqlSession session = MyBatisSqlSessionFactory.openSession()) {
			ProductMapper productMapper = session.getMapper(ProductMapper.class);
			products = productMapper.getProductListByCategory(categoryId);
		}
		return products;
	}

}
