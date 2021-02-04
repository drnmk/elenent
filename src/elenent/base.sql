-- uses postgresql 13
-- src/elenent/schema.sql
-- enter environment: $ sudo su postgres -c psql
-- create admin role: # create role elenent_adm superuser;
-- create database: # create database elenent_db owner elenent_adm;
-- add password to role: # alter role elenent_adm with password 'el1234';
-- add login access to role: # alter role elenent_adm with login;
-- enter database: # \c elenent_db;


-- :name create-table-trait :!
create table trait
( id serial primary key
, subject text not null 
, attribute text not null
, uniq boolean not null 
, single boolean not null 
, vtype text not null );

-- :name create-table-vint :!
create table vint
( id serial primary key
, val int not null );

-- :name create-table-vfloat :!
create table vfloat
( id serial primary key 
, val float not null );

-- :name create-table-vtext :!
create table vtext
( id serial primary key 
, val text not null );

-- :name create-table-vdate :!
create table vdate
( id serial primary key 
, val date not null );

-- :name create-table-vstamp :!
create table vstamp
( id serial primary key 
, val timestamp not null );

-- :name create-table-entity :!
create table entity
( id serial primary key );

-- :name create-table-atom :!
create table atom
( id int primary key 
, entity_id int references entity(id) not null 
, trait_id int references trait(id) not null
, value_id int not null
, ref_id int references atom(id) not null
, valid boolean not null 
, stamp timestamp not null default current_timestamp );

-- :name drop-table-all :!
drop table trait, vint
, vfloat, vtext, vdate
, vstamp, entity, atom;
