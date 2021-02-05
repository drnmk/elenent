-- uses postgresql 13
-- src/elenent/schema.sql
-- enter environment: $ sudo su postgres -c psql
-- create admin role: # create role elenent_adm superuser;
-- create database: # create database elenent_db owner elenent_adm;
-- add password to role: # alter role elenent_adm with password 'el1234';
-- add login access to role: # alter role elenent_adm with login;
-- enter database: # \c elenent_db;

-- :name sql-create-base :!
create extension "pgcrypto";
create table trait
( id uuid primary key default gen_random_uuid()
, subject text not null 
, attribute text not null
, uniq boolean not null 
, single boolean not null 
, vtype text not null );
create table vint
( id uuid primary key default gen_random_uuid()
, val int not null );
create table vfloat
( id uuid primary key default gen_random_uuid()
, val float not null );
create table vtext
( id uuid primary key default gen_random_uuid()
, val text not null );
create table vbool
( id uuid primary key default gen_random_uuid()
, val bool not null );
create table vdate
( id uuid primary key default gen_random_uuid()
, val date not null );
create table vstamp
( id uuid primary key default gen_random_uuid()
, val timestamp not null );
create table atom
( id uuid primary key
, entity uuid not null 
, trait_id uuid references trait(id) not null
, value_id uuid not null
, ref_id uuid not null
, valid boolean not null 
, stamp timestamp not null default current_timestamp );

-- :name sql-remove-base :!
drop extension "pgcrypto";
drop table trait, vint, vfloat
, vbool, vtext, vdate, vstamp, atom;
