insert into currency(id, name, region) values (0, 'American Dollar', 'USA');
insert into currency(id, name, region) values (1, 'Australian Dollar', 'AUS');
insert into currency(id, name, region) values (2, 'Euro', 'EU');
insert into currency(id, name, region) values (3, 'Brazilian Real', 'BRA');


insert into user(name) values ('Henrique');
insert into user(name) values ('Bilbo');
insert into user(name) values ('Frodo');
insert into user(name) values ('Gandalf');

insert into offer(user_id, has, wants, amount, rate) values (4, 0, 1, 1000, 1.4);
insert into offer(user_id, has, wants, amount, rate) values (1, 1, 2, 400, 0.77);
insert into offer(user_id, has, wants, amount, rate) values (2, 2, 1, 100, 0.4);
insert into offer(user_id, has, wants, amount, rate) values (1, 3, 2, 220, 0.77);
insert into offer(user_id, has, wants, amount, rate) values (3, 1, 3, 100, 0.4);
insert into offer(user_id, has, wants, amount, rate) values (4, 1, 2, 420, 0.71);
insert into offer(user_id, has, wants, amount, rate) values (2, 2, 1, 130, 0.42);