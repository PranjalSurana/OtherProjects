DROP TABLE PRODUCT2;
DROP TABLE PRODUCT;
DROP TABLE PRODUCT_DETAIL;
DROP TABLE CATEGORY;
DROP SEQUENCE PRODUCT2_SEQ;

--------------------------------------------------------
--  File created - Sunday-August-05-2018   
--------------------------------------------------------
--------------------------------------------------------
--  DDL for Table CATEGORY
--------------------------------------------------------

  CREATE TABLE "CATEGORY" 
   (	"ID" NUMBER(5,0), 
	"NAME" VARCHAR2(50)
   ) ;
--------------------------------------------------------
--  DDL for Table PRODUCT
--------------------------------------------------------

  CREATE TABLE "PRODUCT" 
   (	"PRODUCTID" NUMBER(5,0), 
	"NAME" VARCHAR2(50), 
	"DESCN" VARCHAR2(1000), 
	"CATEGORY" NUMBER(5,0), 
	"PRODUCT_TYPE_ID" NUMBER(5,0), 
	"PRODUCT_TYPE_NAME" VARCHAR2(20)
   ) ;
--------------------------------------------------------
--  DDL for Table PRODUCT_DETAIL
--------------------------------------------------------

  CREATE TABLE "PRODUCT_DETAIL" 
   (	"PRODUCTID" NUMBER(5,0), 
	"MANUFACTURER" VARCHAR2(50), 
	"SKU" VARCHAR2(20), 
	"UPC" NUMBER(12,0), 
	"MINIMUM_AGE" NUMBER(3,0)
   ) ;
--------------------------------------------------------
--  DDL for Table PRODUCT2
--------------------------------------------------------

  CREATE TABLE "PRODUCT2" 
   (	"PRODUCTID" NUMBER(5,0), 
	"NAME" VARCHAR2(50), 
	"DESCN" VARCHAR2(1000), 
	"CATEGORY" NUMBER(5,0)
   ) ;
--------------------------------------------------------
--  DDL for Sequence PRODUCT2_SEQ
--------------------------------------------------------

   CREATE SEQUENCE  "PRODUCT2_SEQ"  MINVALUE 1 MAXVALUE 9999999999999999999999999999 INCREMENT BY 1 START WITH 61 CACHE 20 NOORDER  NOCYCLE ;
REM INSERTING into CATEGORY
SET DEFINE OFF;
Insert into CATEGORY (ID,NAME) values (1,'Can''t do without');
Insert into CATEGORY (ID,NAME) values (2,'Really don''t need');
REM INSERTING into PRODUCT
SET DEFINE OFF;
Insert into PRODUCT (PRODUCTID,NAME,DESCN,CATEGORY,PRODUCT_TYPE_ID,PRODUCT_TYPE_NAME) values (1,'Widget','This widget is indispensible',1,null,null);
Insert into PRODUCT (PRODUCTID,NAME,DESCN,CATEGORY,PRODUCT_TYPE_ID,PRODUCT_TYPE_NAME) values (2,'Gadget','Must be seen to be believed!',1,null,null);
REM INSERTING into PRODUCT_DETAIL
SET DEFINE OFF;
Insert into PRODUCT_DETAIL (PRODUCTID,MANUFACTURER,SKU,UPC,MINIMUM_AGE) values (1,'Acme Corp','AC-WD-001',0,8);
Insert into PRODUCT_DETAIL (PRODUCTID,MANUFACTURER,SKU,UPC,MINIMUM_AGE) values (2,'Acme Corp','AC-GD-001',0,0);
REM INSERTING into PRODUCT2
SET DEFINE OFF;
--------------------------------------------------------
--  DDL for Trigger PRODUCT2_INSERT
--------------------------------------------------------

  CREATE OR REPLACE TRIGGER "PRODUCT2_INSERT" 
BEFORE INSERT
ON product2
FOR EACH ROW
BEGIN
    IF :NEW.productid IS NULL THEN
        SELECT product2_seq.NEXTVAL 
        INTO   :NEW.productid
        FROM   DUAL;
    END IF;
END;

/
ALTER TRIGGER "PRODUCT2_INSERT" ENABLE;
--------------------------------------------------------
--  Constraints for Table CATEGORY
--------------------------------------------------------

  ALTER TABLE "CATEGORY" ADD PRIMARY KEY ("ID") ENABLE;
  ALTER TABLE "CATEGORY" MODIFY ("ID" NOT NULL ENABLE);
--------------------------------------------------------
--  Constraints for Table PRODUCT
--------------------------------------------------------

  ALTER TABLE "PRODUCT" MODIFY ("CATEGORY" NOT NULL ENABLE);
  ALTER TABLE "PRODUCT" ADD PRIMARY KEY ("PRODUCTID") ENABLE;
  ALTER TABLE "PRODUCT" MODIFY ("PRODUCTID" NOT NULL ENABLE);
--------------------------------------------------------
--  Constraints for Table PRODUCT_DETAIL
--------------------------------------------------------

  ALTER TABLE "PRODUCT_DETAIL" ADD PRIMARY KEY ("PRODUCTID") ENABLE;
  ALTER TABLE "PRODUCT_DETAIL" MODIFY ("MANUFACTURER" NOT NULL ENABLE);
  ALTER TABLE "PRODUCT_DETAIL" MODIFY ("PRODUCTID" NOT NULL ENABLE);
--------------------------------------------------------
--  Constraints for Table PRODUCT2
--------------------------------------------------------

  ALTER TABLE "PRODUCT2" ADD PRIMARY KEY ("PRODUCTID") ENABLE;
  ALTER TABLE "PRODUCT2" MODIFY ("CATEGORY" NOT NULL ENABLE);
  ALTER TABLE "PRODUCT2" MODIFY ("PRODUCTID" NOT NULL ENABLE);
