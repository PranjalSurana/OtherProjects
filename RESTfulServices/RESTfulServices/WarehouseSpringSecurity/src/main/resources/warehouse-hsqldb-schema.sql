-- Schema definition for the widgets and gadgets in the warehouse

-- Drop tables
drop table widgets if exists;
drop table gadgets if exists;

-- Widgets
create table widgets 
	(
	id integer not null primary key, 
	description varchar(45), 
	price numeric(6,2), 
	gears integer, 
	sprockets integer
	);

-- Gadgets
create table gadgets 
	(
	id integer not null primary key, 
	description varchar(45), 
	price numeric(6,2), 
	cylinders integer
	);

