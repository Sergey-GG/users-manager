CREATE table users(
    id int PRIMARY KEY GENERATED ALWAYS AS IDENTITY,
    name varchar(25) not null,
    surname varchar(30) not null,
    email varchar(65) not null,
    role varchar(20) not null
)