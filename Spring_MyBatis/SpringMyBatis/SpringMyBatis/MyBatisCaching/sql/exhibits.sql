--------------------------------------------------------
--  DDL for Table exhibits
--------------------------------------------------------
  DROP TABLE exhibits;
   
  CREATE TABLE exhibits
   (	
    name VARCHAR2(45), 
	permanent CHAR(1), 
	cost NUMBER(*,0)
   );
   
-- INSERTING into exhibits
INSERT INTO exhibits (name, permanent, cost) VALUES ('Picasso', '0', 4200);
INSERT INTO exhibits (name, permanent, cost) VALUES ('OKeefe', '1', 0);
INSERT INTO exhibits (name, permanent, cost) VALUES ('Remington', '1', 0);
INSERT INTO exhibits (name, permanent, cost) VALUES ('Turnbull', '1', 0);
INSERT INTO exhibits (name, permanent, cost) VALUES ('Monet', '0', 850);
INSERT INTO exhibits (name, permanent, cost) VALUES ('Moore', '0', 900);
INSERT INTO exhibits (name, permanent, cost) VALUES ('WangDynasty', '0', 1300);
INSERT INTO exhibits (name, permanent, cost) VALUES ('Amenhotep', '0', 2450);
INSERT INTO exhibits (name, permanent, cost) VALUES ('ElginMarbles', '0', 825);
INSERT INTO exhibits (name, permanent, cost) VALUES ('TogoFolkArt', '0', 735);

COMMIT;