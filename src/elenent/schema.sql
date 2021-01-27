-- uses postgresql 13
-- src/elenent/schema.sql
-- enter environment: $ sudo su postgres -c psql
-- create admin role: # create role elenent_adm superuser;
-- create database: # create database elenent_db owner elenent_adm;
-- add password to role: # alter role elenent_adm with password 'el1234';
-- add login access to role: # alter role elenent_adm with login;
-- enter database: # \c elenent_db;



create table client
( id serial primary key
, created_at timestamp with time zone default current_timestamp );

create table client_info
( id serial primary key
, client_id int references client(id) not null
, name text
, email text
, entered_at timestamp with time zone default current_timestamp );

create table person
( id serial primary key
, client_id int references client(id) not null
, created_at timestamp with time zone default current_timestamp );

create table person_info
( id serial primary key
, person_id int references person(id) not null
, name text
, email text
, phone text
, entered_at timestamp with time zone default current_timestamp );

create table log
( id serial primary key
, person_id int references person(id) not null
, created_at timestamp default now() not null );

create table security
( id serial primary key
, branch text not null
, log_id int references log(id) not null ); 

create table security_tnote
( id serial primary key
, security_id int references log(id) not null
, ticker text
, description text
, multiplier int 
, maturity date
, log_id int references log(id) not null 
, rid int references security_tnote(id)
, op boolean ); 
