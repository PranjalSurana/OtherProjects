CREATE OR REPLACE PROCEDURE proc_products_by_category (
    parm_category NUMBER,
    parm_prod_cur OUT SYS_REFCURSOR
)   
IS   
BEGIN   
    OPEN parm_prod_cur FOR 
	    SELECT productid, name, descn, category 
	    FROM   product 
	    WHERE  category = parm_category;
END;
/

