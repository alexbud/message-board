create table if not exists T_MESSAGE (
	ID int not null auto_increment primary key,
	TS timestamp not null,
	PRINCIPAL varchar(20) not null, TITLE varchar(50) not null, 
	CONTENT varchar(140), 
	SENDER varchar(25), 
	URL varchar(100));
