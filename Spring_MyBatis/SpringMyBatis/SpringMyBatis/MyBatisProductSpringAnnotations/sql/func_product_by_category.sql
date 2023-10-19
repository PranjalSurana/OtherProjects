CREATE OR REPLACE FUNCTION func_products_by_category (
    parm_category NUMBER
) 
RETURN SYS_REFCURSOR
IS   
    prod_cur SYS_REFCURSOR;
BEGIN   
    OPEN prod_cur FOR 
	    SELECT productid, name, descn, category 
	    FROM   product 
	    WHERE  category = parm_category;
    RETURN prod_cur;
END;
/

