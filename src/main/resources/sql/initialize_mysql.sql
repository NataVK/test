drop database shop;
CREATE DATABASE shop CHARACTER SET utf8 COLLATE utf8_unicode_ci;

ALTER TABLE item CONVERT TO CHARACTER SET utf8 COLLATE utf8_unicode_ci;
ALTER TABLE itemmeasure CONVERT TO CHARACTER SET utf8 COLLATE utf8_unicode_ci;
ALTER TABLE itemorderinfo CONVERT TO CHARACTER SET utf8 COLLATE utf8_unicode_ci;
ALTER TABLE orders CONVERT TO CHARACTER SET utf8 COLLATE utf8_unicode_ci;
ALTER TABLE account CONVERT TO CHARACTER SET utf8 COLLATE utf8_unicode_ci;

insert into itemmeasure(id,name) values(1,'кг');
insert into itemmeasure(id,name) values(2,'шт.');
insert into itemmeasure(id,name) values(3,'метр');