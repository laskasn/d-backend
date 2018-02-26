create database danaosdb encoding 'UTF8';
\connect danaosdb;
CREATE EXTENSION "uuid-ossp";
create table country (id uuid primary key, name varchar(500) not null, icon bytea);
create table port (id uuid primary key, name varchar(500) not null, longitude float(25), latitude float(25));
create table vessel (id uuid primary key, name varchar(500) not null, country uuid REFERENCES country(id), image bytea);
create table visits (id uuid primary key, vessel uuid REFERENCES vessel(id), port uuid REFERENCES port(id), visittime timestamp);
