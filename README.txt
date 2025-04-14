creating a db 

create database u24566552_u24564584_northwind
//import schema
mysql -u marcel -p u24566552_u24564584_northwind < northwind-schema.sql
//import sqldimp
mysql -u marcel -p u24566552_u24564584_northwind < northwind-data.sql

mysql -u ms -p u24566552_u24564584_northwind < northwind-data.sql
mysql -u ms -p u24566552_u24564584_northwind < northwind-schema.sql

CREATE USER 'ms'@'localhost' IDENTIFIED BY 'Need4Speed@!!';

GRANT ALL PRIVILEGES ON *.* TO 'ms'@'localhost';