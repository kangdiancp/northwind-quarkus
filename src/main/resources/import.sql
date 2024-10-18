-- This file allow to write SQL commands that will be emitted in test and dev.
-- The commands are commented as their support depends of the database
-- insert into myentity (id, field) values(1, 'field-1');
-- insert into myentity (id, field) values(2, 'field-2');
-- insert into myentity (id, field) values(3, 'field-3');
-- alter sequence myentity_seq restart with 4;


insert into category(category_id,category_name,description)values(1,'tv','electronic');
insert into category(category_id,category_name,description)values(2,'hp','smart');
insert into category(category_id,category_name,description)values(3,'radio','jadul');
alter sequence Category_SEQ restart with 4;