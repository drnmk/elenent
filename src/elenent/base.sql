-- uses postgresql 13
-- src/elenent/schema.sql
-- enter environment: $ sudo su postgres -c psql
-- create admin role: # create role elenent_adm superuser;
-- create database: # create database elenent_db owner elenent_adm;
-- add password to role: # alter role elenent_adm with password 'el1234';
-- add login access to role: # alter role elenent_adm with login;
-- enter database: # \c elenent_db;

-- :name sql-create-base :!
create table trait
( key text primary key
, uni text not null 
, cardi text  not null 
, vtype text not null
, stamp timestamp not null default current_timestamp );
create table atom
( id uuid primary key
, ent uuid not null 
, tkey text references trait(key) not null
, val text not null
, rid uuid not null
, valid boolean not null 
, stamp timestamp not null default current_timestamp );

-- :name sql-remove-base :!
drop table trait, atom;
