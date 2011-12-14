insert into T_USER (TS, USERNAME, PASSWORD) values (now(), 'admin', 'jGl25bVBBBW96Qi9Te4V37Fnqchz/Eu4qB9vKrRIqRg=');
insert into T_USER (TS, USERNAME, PASSWORD) values (now(), 'viewer', '01ylBRuC/8Mmo7C2V0qaMWHe4WuUeKGZ7jnNgDzlt5k=');
insert into T_USER (TS, USERNAME, PASSWORD) values (now(), 'member', '4xq2Q8RPeg7IJLWdEZTWDawzQgDYReYdLSidqg8IfqQ=');

insert into T_AUTHORITIES (USERNAME, AUTHORITY) values ('admin', 'ROLE_ADMIN');
insert into T_AUTHORITIES (USERNAME, AUTHORITY) values ('admin', 'ROLE_MEMBER');
insert into T_AUTHORITIES (USERNAME, AUTHORITY) values ('member', 'ROLE_MEMBER');
insert into T_AUTHORITIES (USERNAME, AUTHORITY) values ('viewer', 'ROLE_VIEWER');