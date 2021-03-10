create database rzd;

create table drivers(
    id int primary key generated always as identity,
    name varchar(50) not null,
    birthdate date not null,
    bid numeric(3, 2) default 1.00,
    rating numeric(3, 2) not null default 4.50 check (rating between 1.00 and 5.00),
    train_id int,
    sex char not null check (sex in ('m', 'f', 'M', 'F')),
    login varchar(20) not null,
    password varchar(20) not null,
    hired date not null default current_date
);

create table trains(
    number int primary key not null,
    driver_id int not null,
    route_id int not null check (route_id between 1000 and 2000)
);

alter table drivers add foreign key (train_id) references trains(number);

create table departments(
    id int primary key generated always as identity,
    name varchar(35) not null
);

create table stations(
    code int primary key check (code between 1 and 999999),
    name varchar(20) not null,
    department_id int not null references departments(id)
);

create table schedule(
    id int primary key generated always as identity,
    route_id int not null check (route_id between 1000 and 2000),
    station int references stations(code),
    time time not null
);

create table posts(
    id int primary key generated always as identity,
    name varchar(35) not null,
    salary numeric(8, 2) not null
);

create table employees(
    id int primary key generated always as identity,
    name varchar(50) not null,
    birthdate date not null,
    post_id int references posts(id),
    bid numeric(3, 2) default 1.00,
    sex char not null check (sex in ('m', 'f', 'M', 'F')),
    hired date not null default current_date
);

copy departments(name) from 'C:/db/departments.txt';
copy posts(name, salary) from 'C:/db/posts.txt'
with (format csv);

copy drivers(name, birthdate, bid, train_id, rating, sex, login, password, hired)
from 'C:/db/drivers.txt'
with (format csv, encoding 'WIN1251');


--триггер для приведения букв пола к одному формату