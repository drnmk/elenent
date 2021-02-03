-- uses postgresql 13
-- src/elenent/schema.sql
-- enter environment: $ sudo su postgres -c psql
-- create admin role: # create role elenent_adm superuser;
-- create database: # create database elenent_db owner elenent_adm;
-- add password to role: # alter role elenent_adm with password 'el1234';
-- add login access to role: # alter role elenent_adm with login;
-- enter database: # \c elenent_db;


-- :name undefine-table-all :!
-- :doc delete all tables, for dev only
drop table client_info, client, log;

-- :name define-log :!
create table log
( id serial primary key
, by int not null
, at timestamp not null default current_timestamp );

-- :name define-client :!
-- :doc declarative table for client
create table client
( id serial primary key
, branch text not null );

-- :name define-client-info :!
-- :doc accumulative table pertaining to client. 
create table client_info
( id serial primary key
, client_id int references client(id) not null
, name text not null
, email text not null
, log_id int references log(id) not null );



/* 

-- :name undefine-table-all :!
-- :doc delete all tables, for dev only
drop table client_info, client, log;

-- :name define-table-log :!
create table log
( id int primary key
, tb text not null
, rf int not null
, op boolean not null
, by int not null
, at timestamp not null default current_timestamp );

-- :name define-table-client :!
-- :doc declarative table for client
create table client
( id int primary key );

-- :name define-table-client-info :!
-- :doc accumulative table pertaining to client. 
create table client_info
( id int primary key
, client_id int references client(id) not null
, name text not null
, email text not null );


create table person
( id serial primary key
, client_id int references client(id) not null
, created_at timestamp with time zone default current_timestamp );

create table person_info
( id serial primary key
, person_id int references person(id) not null
, name text not null 
, email text not null
, phone text not null
, entered_at timestamp with time zone default current_timestamp );

create table job
( id serial primary key
, ordered_by int 
, executed_at timestamp default now() not null );

create table log
( key int primary key
, tbl text not null
, rfr int default 0
, opr boolean not null
, jid int references job(id) not null );

create table portfolio
( key int primary key
, client_id int not null
, name text not null
, opened_on date not null
, 


);



create table asset
( id int primary key
, branch text not null ); 

create table tnote_def
( id int primary key
, security_id int references security(id) not null
, ticker text not null 
, description text not null
, multiplier int not null 
, matured_on date not null); 

create table security_index_def
( id int primary key
, asset_id int references security(id) not null
, ticker text not null 
, description text not null 
, multiplier int not null); 

create table contract
( id int primary key
, branch text not null );

create table future_def
( id int primary key
, exchange_id int references exchange(id) not null
, cme_globex text not null
, cme_clearport text not null
, clearing text not null
, subgroup text not null
, sub_category text not null
, description text not null ); 

create table contract_future
( id int primary key
, derivative_id int references derivative(id) not null
, exchange_id int references exchange(id) not null
, name text not null 
, underlying_id int not null
, traded_on date not null
, matured_on date not null
, terminated_on date not null
, description text not null ); 





-- lags
-- modified following
-- modified prior
-- none
-- pure following
-- pure prior 

