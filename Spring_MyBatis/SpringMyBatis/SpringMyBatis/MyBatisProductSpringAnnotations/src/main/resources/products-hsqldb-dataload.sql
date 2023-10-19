-- Data inserts    
INSERT INTO category
VALUES (1, 'Can''t do without');

INSERT INTO category
VALUES (2, 'Really don''t need');


INSERT INTO product (productid, name, descn, category)
VALUES (1, 'Widget', 'This widget is indispensible', 1);
INSERT INTO product (productid, name, descn, category)
VALUES (2, 'Gadget', 'Must be seen to be believed!', 1);



INSERT INTO
    product_detail
VALUES (1, 'Acme Corp', 'AC-WD-001', 0, 8);

INSERT INTO
    product_detail
VALUES (2, 'Acme Corp', 'AC-GD-001', 0, 0);

-- Commit
COMMIT;