-- category
CREATE TABLE category (
    id        NUMERIC(5,0) NOT NULL PRIMARY KEY,
    name      VARCHAR(50)
);

-- product
CREATE TABLE product (
    productid NUMERIC(5,0) NOT NULL PRIMARY KEY, 
	name      VARCHAR(50), 
	descn     VARCHAR(1000), 
	category  NUMERIC(5,0) NOT NULL
);

ALTER TABLE product
ADD CONSTRAINT product_category_fk
    FOREIGN KEY (category)
    REFERENCES category(id);

-- product_detail
CREATE TABLE product_detail (
    productid    NUMERIC(5,0) NOT NULL PRIMARY KEY,
    manufacturer VARCHAR(50) NOT NULL,
    sku          VARCHAR(20),
    upc          NUMERIC(12,0),
    minimum_age  NUMERIC(3,0),
    CONSTRAINT product_detail_product_fk FOREIGN KEY (productid) REFERENCES product
);

-- product with IDENTITY key
CREATE TABLE product2 (
    productid INTEGER GENERATED ALWAYS AS IDENTITY(START WITH 1) PRIMARY KEY, 
	name      VARCHAR(50), 
	descn     VARCHAR(1000), 
	category  NUMERIC(5,0) NOT NULL,
	CONSTRAINT product2_category_fk FOREIGN KEY (category) REFERENCES category(id)
);

-- create this sequence as a dummy so that the Oracle based tests will continue to work with HyperSQL
-- (you would not normally do this)
CREATE SEQUENCE product2_seq START WITH 100;


-- product type (these columns are always on the table, but shown separately here as they are not used)
ALTER TABLE product
    ADD product_type_id NUMERIC(5,0);
ALTER TABLE product
    ADD product_type_name VARCHAR(20);
