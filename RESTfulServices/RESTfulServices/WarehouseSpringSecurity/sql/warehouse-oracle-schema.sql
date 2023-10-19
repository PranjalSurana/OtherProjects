--------------------------------------------------------
-- Drop tables
-- Oracle will complain in the tables do not exist
--------------------------------------------------------
DROP TABLE WIDGETS;
DROP TABLE GADGETS;

--------------------------------------------------------
--  DDL for Table GADGETS
--------------------------------------------------------

  CREATE TABLE GADGETS
   (
    ID NUMBER(*,0) not null primary key, 
    DESCRIPTION VARCHAR2(45), 
	PRICE NUMBER(*,2), 
	CYLINDERS NUMBER(*,0)
   );
--------------------------------------------------------
--  DDL for Table WIDGETS
--------------------------------------------------------

  CREATE TABLE WIDGETS 
   (	
    ID NUMBER(*,0) not null primary key, 
	DESCRIPTION VARCHAR2(45), 
	PRICE NUMBER(*,2), 
	GEARS NUMBER(*,0), 
	SPROCKETS NUMBER(*,0)
   );
