CREATE OR REPLACE PROCEDURE proc_del_products_by_category (
    parm_category NUMBER
)   
IS   
BEGIN   
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
END;
/

