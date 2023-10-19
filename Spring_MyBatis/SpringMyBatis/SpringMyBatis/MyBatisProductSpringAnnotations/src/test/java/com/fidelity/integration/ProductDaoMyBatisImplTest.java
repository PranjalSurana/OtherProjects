package com.fidelity.integration;

import static org.junit.jupiter.api.Assertions.*;

import java.util.List;
import java.util.Map;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.transaction.annotation.Transactional;

import com.fidelity.domain.Category;
import com.fidelity.domain.Product;
import com.fidelity.domain.ProductDetail;
import com.fidelity.domain.ProductType;

@ExtendWith(SpringExtension.class)
@ContextConfiguration(locations = "classpath:product-beans.xml")
class ProductDaoMyBatisImplTest {

	@Autowired
	private ProductDao dao;
	
	private Product product1;
	private Product product1WithDetails;
	
	@BeforeEach
	void setUp() {
		product1 = new Product(1, 1, "Widget", "This widget is indispensible");
		product1WithDetails = new Product(1, 1, "Widget", "This widget is indispensible",
				new ProductDetail(1, "Acme Corp", "AC-WD-001", "0", 8));
	}
	
	@Test
	void testDaoExists() {
		assertNotNull(dao);
	}
	
	@Test
	void testGetProducts() {
		List<Product> products = dao.getProducts();
			
		assertNotNull(products);
		assertEquals(2, products.size());
		for (Product product : products) {
			assertNotNull(product);
		}
		assertTrue(products.contains(product1));
	}
	
	@Test
	void testGetProduct() {
		Product product = dao.getProduct(1);
			
		assertEquals(product1, product);
	}
	
	@Test
	void testGetProductsWithDetail() {
		List<Product> products = dao.getProductsWithDetail();
			
		assertNotNull(products);
		assertEquals(2, products.size());
		for (Product product : products) {
			assertNotNull(product);
		}
		assertTrue(products.contains(product1WithDetails));
	}

	@Test
	void testGetProductsWithNestedDetail() {
		List<Product> products = dao.getProductsWithNestedDetail();
			
		assertNotNull(products);
		assertEquals(2, products.size());
		for (Product product : products) {
			assertNotNull(product);
		}
		assertTrue(products.contains(product1WithDetails));
	}

	@Test
	void testGetProductsWithNestedSelect() {
		List<Product> products = dao.getProductsWithNestedSelect();
			
		assertNotNull(products);
		assertEquals(2, products.size());
		for (Product product : products) {
			assertNotNull(product);
		}
		assertTrue(products.contains(product1WithDetails));
	}

	@Test
	void testGetCategoriesWithNestedProduct() {
		List<Category> categories = dao.getCategoriesWithNestedProduct();
			
		assertNotNull(categories);
		assertEquals(2, categories.size());
		for (Category category : categories) {
			assertNotNull(category);
			if (category.getCategoryId() == 1) {
				assertEquals("Can't do without", category.getName());
				assertTrue(category.getProducts().contains(new Product(1, 1, "Widget", "This widget is indispensible")));
			}
		}
	}

	@Test
	@Transactional
	void testInsertProduct() {
		Product expected = new Product(3, 1, "Whatsit", "Unbelievable!");
		List<Product> products = dao.getProducts();
		assertFalse(products.contains(expected));
		int expectedRows = products.size() + 1;

		assertTrue(dao.insertProduct(expected));

		products = dao.getProducts();
		assertTrue(products.contains(expected));
		assertEquals(expectedRows, products.size());
	}
	
	@Test
	@Transactional
	void testUpdateProduct() {
		Product expected = new Product(1, 2, "Whatsit", "Unbelievable!");
		List<Product> products = dao.getProducts();
		assertFalse(products.contains(expected));

		assertTrue(dao.updateProduct(expected));

		products = dao.getProducts();
		assertTrue(products.contains(expected));
	}

	@Test
	@Transactional
	void testDeleteProduct() {
		// This is not ideal, but we have to insert a product first
		// As this is currently defined, cannot delete any product that has a details row
		Product product3 = new Product(3, 1, "Whatsit", "Unbelievable!");
		assertTrue(dao.insertProduct(product3));

		int expectedRows = dao.getProducts().size() - 1;
		
		assertTrue(dao.deleteProduct(product3.getProductId()));

		List<Product> products = dao.getProducts();
		assertFalse(products.contains(product3));
		assertEquals(expectedRows, products.size());
	}

	@Test
	@Transactional
	void testInsertWithIdentity() {
		product1.setProductId(0);
		// Just double-check that it was updated - guards against future immutability
		assertEquals(0, product1.getProductId());
		List<Product> products = dao.getProducts2();
		assertEquals(0, products.size());

		assertTrue(dao.insertProductWithIdentity(product1));

		// Check that productId was set to the new key value
		assertNotEquals(0, product1.getProductId());
		products = dao.getProducts2();
		assertTrue(products.contains(product1));
    }

	@Test
	@Transactional
	void testInsertWithSequence() {
		product1.setProductId(0);
		// Just double-check that it was updated - guards against future immutability
		assertEquals(0, product1.getProductId());
		List<Product> products = dao.getProducts2();
		assertEquals(0, products.size());

		assertTrue(dao.insertProductWithSequence(product1));

		// Check that productId was set to the new key value
		assertNotEquals(0, product1.getProductId());
		products = dao.getProducts2();
		assertTrue(products.contains(product1));
    }

	@Test
	@Transactional
	void testEnumWithTypeId() {
		product1.setType(ProductType.BIG);
		product1.setProductId(999);
		// Just double-check that it was updated - guards against future immutability
		assertEquals(ProductType.BIG, product1.getType());
		List<Product> products = dao.getProductsWithTypeId();
		assertEquals(0, products.size());

		assertTrue(dao.insertProductWithTypeId(product1));

		products = dao.getProductsWithTypeId();
		assertEquals(1, products.size());
		assertTrue(products.contains(product1));
    }

	@Test
	@Transactional
	void testEnumWithTypeName() {
		product1.setType(ProductType.BIG);
		product1.setProductId(999);
		// Just double-check that it was updated - guards against future immutability
		assertEquals(ProductType.BIG, product1.getType());
		List<Product> products = dao.getProductsWithTypeName();
		assertEquals(0, products.size());

		assertTrue(dao.insertProductWithTypeName(product1));

		products = dao.getProductsWithTypeName();
		assertEquals(1, products.size());
		assertTrue(products.contains(product1));
    }

	@Test
	void testGetProductsByCategoryAndNameMap() {
		List<Product> products = dao.getProductsByCategoryAndNameMap(1, "W");
			
		assertNotNull(products);
		assertEquals(1, products.size());
		assertTrue(products.contains(product1));
	}
	
	@Test
	void testGetProductsByCategoryAndNameParam() {
		List<Product> products = dao.getProductsByCategoryAndNameParam(1, "W");
			
		assertNotNull(products);
		assertEquals(1, products.size());
		assertTrue(products.contains(product1));
	}
	
	@Test
	void testGetProductsAsMap() {
		Map<Integer, Product> products = dao.getProductsAsMap();
		assertEquals(product1, products.get(1));
	}
	
	@Test
	void testGetProductsWithBounds() {
		List<Product> products = dao.getProductsByBounds(0, 1);
		assertEquals(1, products.size());
		products = dao.getProductsByBounds(1, 1);
		assertEquals(1, products.size());
	}
		
	@Test
	void testGetProductIdNameMap() {
		Map<Integer, String> products = dao.getProductIdNameMap();
		assertEquals(2, products.size());
		assertEquals("Widget", products.get(1));
		assertEquals("Gadget", products.get(2));
	}
		
}
