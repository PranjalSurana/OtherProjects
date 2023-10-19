package com.fidelity.integration;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Many;
import org.apache.ibatis.annotations.MapKey;
import org.apache.ibatis.annotations.One;
import org.apache.ibatis.annotations.Options;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Result;
import org.apache.ibatis.annotations.ResultMap;
import org.apache.ibatis.annotations.Results;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.SelectKey;
import org.apache.ibatis.annotations.Update;
import org.apache.ibatis.session.RowBounds;

import com.fidelity.domain.Category;
import com.fidelity.domain.Product;
import com.fidelity.domain.ProductDetail;


public interface ProductMapper {

    @Select("""
			SELECT productid, name, descn as description, category as categoryId 
    		  FROM product
		""")
    List<Product> getProducts();

    @Select("""
			SELECT productid, name, descn, category 
    		  FROM product 
    		 WHERE productid = #{productId}
		""")
	@Result(property="productId",   column="PRODUCTID", id=true)
	@Result(property="name",        column="NAME")
	@Result(property="description", column="DESCN")
	@Result(property="categoryId",  column="CATEGORY")
    Product getProduct(int productId);

    @Select("""
			SELECT productid, name, descn, category 
    		  FROM product 
    		 WHERE category = #{value}
		""")
    @ResultMap("com.fidelity.integration.ProductMapper.ProductMap")
    List<Product> getProductListByCategory(int categoryId);

    @Insert("""
			INSERT INTO product (productid, name, descn, category) 
    		VALUES (#{productId}, #{name}, #{description}, #{categoryId})
    	""")
	int insertProduct(Product product);

    // ResultMap extension not supported in annotations because it makes no sense when ResultMaps are not named
    @Select("""
			SELECT p.productid, p.name, p.descn, p.category, d.manufacturer, d.sku, d.upc, d.minimum_age 
    		  FROM product p 
    		JOIN   product_detail d 
    		ON     p.productid = d.productid
		""")
    @ResultMap("com.fidelity.integration.ProductMapper.ProductWithDetailByExtension")
    List<Product> getProductsWithDetail();

    // Nested ResultMap not supported in annotations due to limitations with Java annotations
    @Select("""
			SELECT p.productid, p.name, p.descn, p.category, d.manufacturer, d.sku, d.upc, d.minimum_age 
    		  FROM product p 
    		JOIN   product_detail d 
    		ON     p.productid = d.productid
		""")
    @ResultMap("com.fidelity.integration.ProductMapper.ProductWithNestedDetailMap")
	List<Product> getProductsWithNestedDetail();

    @Select("""
			SELECT productid, name, descn, category 
    		  FROM product
		""")
	@Result(property="productId",   column="PRODUCTID", id=true)
	@Result(property="name",        column="NAME")
	@Result(property="description", column="DESCN")
	@Result(property="categoryId",  column="CATEGORY")
	@Result(property="detail",      column="PRODUCTID",
			one=@One(select="com.fidelity.integration.ProductMapper.getProductDetail"))
	List<Product> getProductsWithNestedSelect();
	
	@Select("""
			SELECT productid, manufacturer, sku, upc, minimum_age 
			  FROM product_detail 
			 WHERE productid = #{value}
		""")
	@Result(property="productId",    column="PRODUCTID", id=true)
	@Result(property="manufacturer", column="MANUFACTURER")
	@Result(property="sku",          column="SKU")
	@Result(property="upc",          column="UPC")
	@Result(property="minimumAge",   column="MINIMUM_AGE")
	ProductDetail getProductDetail(int productId);

    @Select("""
			SELECT p.productid, p.name, p.descn, p.category, c.id, c.name AS cat_name 
    		  FROM category c 
    		  LEFT OUTER JOIN 
    		       product p 
    		    ON p.category = c.id
		""")
    @ResultMap("com.fidelity.integration.ProductMapper.CategoryWithNestedProductMap")
	List<Category> getCategoriesWithNestedProduct();

    @Select("""
			SELECT id, name AS cat_name 
    		  FROM category
		""")
	@Result(property="categoryId",  column="ID", id=true)
	@Result(property="name",        column="CAT_NAME")
	@Result(property="products",    column="ID",
			many=@Many(select="com.fidelity.integration.ProductMapper.getProductListByCategory"))
    List<Category> getCategoriesWithNestedSelect();

	@Update("""
			UPDATE product
			   SET name = #{name}, descn = #{description}, category = #{categoryId} 
			 WHERE productid = #{productId}
		""")
	int updateProduct(Product product);
	
	@Delete("""
			DELETE FROM product 
			 WHERE productid = #{value}
		""")
	int deleteProduct(int productId);

    @Insert("""
			INSERT INTO product2 (name, descn, category) 
    		     VALUES (#{name}, #{description}, #{categoryId})
		""")
    @Options(useGeneratedKeys=true, keyProperty="productId", keyColumn="productid")
    int insertProductWithIdentity(Product product);

    @Insert("""
			INSERT INTO product2 (productid, name, descn, category) 
    		     VALUES (#{productId}, #{name}, #{description}, #{categoryId})
		""")
    @SelectKey(statement="SELECT product2_seq.NEXTVAL FROM DUAL", keyProperty="productId", 
			resultType=int.class, before=true)
    int insertProductWithSequence(Product product);

    List<Product> getProducts2();

	List<Product> getProductsWithTypeId();
	List<Product> getProductsWithTypeName();
	int insertProductWithTypeId(Product product);
	int insertProductWithTypeName(Product product);
	
	List<Product> getProductsByCategoryAndNameMap(Map<Object, Object> map);
	
    @Select("""
			SELECT productid, name, descn, category 
    		  FROM product 
    		 WHERE category = #{categoryId} 
    		   AND name     LIKE #{name} || '%'
		""")
    @ResultMap("com.fidelity.integration.ProductMapper.ProductMap")
    List<Product> getProductsByCategoryAndNameParam(
    		@Param("categoryId") int categoryId, 
    		@Param("name") String name);

	List<Product> getProducts(RowBounds bounds);

    @Select("""
			SELECT productid, name, descn as description, category as categoryId 
    		  FROM product
		""")
    @MapKey("productId")
    Map<Integer, Product> getProductsAsMap();

}
