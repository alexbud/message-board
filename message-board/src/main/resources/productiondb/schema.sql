drop database if exists messages;
create database messages;

create table if not exists T_USER (
	TS timestamp not null,
	USERNAME varchar(20) not null primary key, 
	PASSWORD varchar(140) not null); 
	
create table if not exists T_AUTHORITIES (
	ID int not null auto_increment primary key,
	USERNAME varchar(50), 
	AUTHORITY varchar(50) not null);

drop index IX_T_AUTHORITIES_U on T_AUTHORITIES;
create unique index IX_T_AUTHORITIES_U on T_AUTHORITIES (USERNAME, AUTHORITY) using HASH;
	
create table if not exists T_MESSAGE (
	ID int not null auto_increment primary key,
	TS timestamp not null,
	PRINCIPAL varchar(20) not null, 
	TITLE varchar(50) not null, 
	CONTENT varchar(140), 
	URL varchar(100));
