drop table T_AUTHORITIES if exists;
drop table T_USER if exists;

create table T_USER (
	USERNAME varchar(50) not null primary key,
	PASSWORD varchar(50) not null,
	TS timestamp not null);
	
create table T_AUTHORITIES (
	ID integer identity primary key,
	USERNAME varchar(50), 
	AUTHORITY varchar(50) not null);
	
drop table T_MESSAGE if exists;

create table T_MESSAGE (
	ID integer identity primary key,
	TS timestamp not null,
	PRINCIPAL varchar(20) not null, 
	TITLE varchar(50) not null, 
	CONTENT varchar(140), 
	URL varchar(100));