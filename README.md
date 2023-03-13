# About
A simple Rest API for a grocery store with the possibility to add and remove products, add new promotions, add and remove products from those promotions. 
The API also supports adding items to a shopping cart which automatically applies any available promotions. The application supports Swagger UI for easier use of methods.

It is built using Spring Boot, JPA and Lombok.
The used DB is MariaDB


## Instructions

1. Clone the repo
2. Generate or connect to a DB and use the SQL scripts to generate a table that will work with the API
```
create table groceries_shop.groceries
(
    id    int auto_increment
        primary key,
    name  varchar(255)                                          not null,
    price int                                                   not null,
    deal  enum ('NONE', 'TWOFORTHREE', 'BUYONEGETONEHALFPRICE') not null
);
```

3. Enjoy! :)
