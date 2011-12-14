insert into T_USER (TS, USERNAME, PASSWORD) values (now(), 'admin', 'password');
insert into T_USER (TS, USERNAME, PASSWORD) values (now(), 'member', 'password');
insert into T_USER (TS, USERNAME, PASSWORD) values (now(), 'member2', 'password');
insert into T_USER (TS, USERNAME, PASSWORD) values (now(), 'viewer', 'password');

insert into T_AUTHORITIES (USERNAME, AUTHORITY) values ('admin', 'ROLE_ADMIN');
insert into T_AUTHORITIES (USERNAME, AUTHORITY) values ('admin', 'ROLE_MEMBER');
insert into T_AUTHORITIES (USERNAME, AUTHORITY) values ('member', 'ROLE_MEMBER');
insert into T_AUTHORITIES (USERNAME, AUTHORITY) values ('member2', 'ROLE_MEMBER');
insert into T_AUTHORITIES (USERNAME, AUTHORITY) values ('viewer', 'ROLE_VIEWER');

insert into T_MESSAGE (PRINCIPAL, TS, TITLE, CONTENT, URL) values ('admin', now(), 'message one', 'Hello message 1', 'http://www.hotmail.com/one');
insert into T_MESSAGE (PRINCIPAL, TS, TITLE, CONTENT, URL) values ('admin', now(), 'message two', 'Hello message 2', 'http://www.hotmail.com/two');
insert into T_MESSAGE (PRINCIPAL, TS, TITLE, CONTENT, URL) values ('admin', now(), 'message three', 'Hello message 3', 'http://www.hotmail.com/three');
insert into T_MESSAGE (PRINCIPAL, TS, TITLE, CONTENT, URL) values ('admin', now(), 'message four', 'Hello message 4', 'http://www.hotmail.com/four');