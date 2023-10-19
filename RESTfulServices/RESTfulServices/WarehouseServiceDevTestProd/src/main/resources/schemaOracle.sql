-- Schema definition for the widgets and gadgets

-- Widgets
create table widgets (id integer not null primary key, description varchar(45), price numeric(6,2), gears integer, sprockets integer);

-- Gadgets
create table gadgets (id integer not null primary key, description varchar(45), price numeric(6,2), cylinders integer);

