--------------------------------------------------------
--  File created - Sunday-August-05-2018   
--------------------------------------------------------
--------------------------------------------------------
--  DDL for Table PRODUCT
--------------------------------------------------------

  CREATE TABLE "PRODUCT" 
   (	"PRODUCTID" NUMBER(5,0), 
	"NAME" VARCHAR2(50), 
	"DESCN" VARCHAR2(1000), 
	"CATEGORY" NUMBER(5,0)
   ) ;
REM INSERTING into PRODUCT
SET DEFINE OFF;
Insert into PRODUCT (PRODUCTID,NAME,DESCN,CATEGORY) values (1,'Widget','This widget is indispensible',1);
Insert into PRODUCT (PRODUCTID,NAME,DESCN,CATEGORY) values (2,'Gadget','Must be seen to be believed!',1);
--------------------------------------------------------
--  Constraints for Table PRODUCT
--------------------------------------------------------

  ALTER TABLE "PRODUCT" MODIFY ("CATEGORY" NOT NULL ENABLE);
  ALTER TABLE "PRODUCT" ADD PRIMARY KEY ("PRODUCTID") ENABLE;
  ALTER TABLE "PRODUCT" MODIFY ("PRODUCTID" NOT NULL ENABLE);
