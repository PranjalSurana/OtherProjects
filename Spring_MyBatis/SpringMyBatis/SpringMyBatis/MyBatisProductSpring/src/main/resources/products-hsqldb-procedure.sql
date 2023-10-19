    
CREATE PROCEDURE proc_products_by_category(
	IN parm_category NUMERIC(5,0)
)
READS SQL DATA
DYNAMIC RESULT SETS 1
BEGIN ATOMIC
	DECLARE prod_cur CURSOR WITH RETURN FOR
	    SELECT productid, name, descn, category 
	    FROM   product 
	    WHERE  category = parm_category;
	OPEN prod_cur;
END
#
    
CREATE FUNCTION func_products_by_category(
	IN parm_category NUMERIC(5,0)
)
RETURNS TABLE(
	productid NUMERIC(5,0), 
	name      VARCHAR(50), 
	descn     VARCHAR(1000), 
	category  NUMERIC(5,0)
)
READS SQL DATA
BEGIN ATOMIC
	RETURN TABLE(
	    SELECT productid, name, descn, category 
	    FROM   product 
	    WHERE  category = parm_category
	);
END
#

CREATE PROCEDURE proc_del_products_by_category(
	IN parm_category NUMERIC(5,0)
)
MODIFIES SQL DATA
BEGIN ATOMIC
	DELETE FROM
	       product_detail
	WHERE  productid IN (
		SELECT productid
		FROM   product
		WHERE  category = parm_category
	);
	
    DELETE FROM
           product
    WHERE  category = parm_category;

END
#
