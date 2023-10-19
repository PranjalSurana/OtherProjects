-- Inserting data into the widget & gadget database

-- Widgets
insert into widgets values (1, 'Low Impact Widget', 12.99, 2, 3);
insert into widgets values (2, 'Medium Impact Widget', 42.99, 5, 5);
insert into widgets values (3, 'High Impact Widget', 89.99, 10, 8);
insert into widgets values (4, 'Super High Impact Widget', 89.99, 100, 18);

-- Gadgets
insert into gadgets values (1, 'Two Cylinder Gadget', 19.99, 2); 
insert into gadgets values (2, 'Four Cylinder Gadget', 29.99, 4); 
insert into gadgets values (3, 'Eight Cylinder Gadget', 49.99, 8); 
insert into gadgets values (4, 'Sixteen Cylinder Gadget', 49.99, 16); 


-- Commit
commit;