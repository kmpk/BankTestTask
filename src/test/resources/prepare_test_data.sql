delete
from bank.account;
delete
from bank.user;

insert into bank.user(id, birth_date, email, full_name, login, password, phone)
values (1, '2024-03-04', '1@email.com', 'name1', 'login1', 'password1', '11111'),
       (2, '2024-03-04', '2@email.com', 'name2', 'login2', 'password2', '11112'),
       (3, '2024-03-04', '3@email.com', 'name3', 'login3', 'password3', '11113'),
       (4, '2024-03-04', '4@email.com', 'name4', 'login4', 'password4', '11114'),
       (5, '2024-03-04', '5@email.com', 'name5', 'login5', 'password5', '11115'),
       (6, '2024-03-04', '6@email.com', 'name6', 'login6', 'password6', '11116'),
       (7, '2024-03-04', '7@email.com', 'name7', 'login7', 'password7', '11117'),
       (8, '2024-03-04', '8@email.com', 'name8', 'login8', 'password8', '11118'),
       (9, '2024-03-04', '9@email.com', 'name9', 'login9', 'password9', '11119'),
       (10, '2024-03-04', '10@email.com', 'name10', 'login10', 'password10', '11120');

insert into bank.account(id, balance, initial_balance)
values (1, 1000, 1000),
       (2, 1000, 1000),
       (3, 1000, 1000),
       (4, 1000, 1000),
       (5, 1000, 1000),
       (6, 1000, 1000),
       (7, 1000, 1000),
       (8, 1000, 1000),
       (9, 1000, 1000),
       (10, 1000, 1000);