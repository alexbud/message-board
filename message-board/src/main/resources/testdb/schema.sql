drop table T_MESSAGE if exists;

create table T_MESSAGE (
	ID integer identity primary key,
	TS timestamp not null,
	PRINCIPAL varchar(20) not null, 
	TITLE varchar(50) not null, 
	CONTENT varchar(140), 
	SENDER varchar(25), 
	URL varchar(100));