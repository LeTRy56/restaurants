DELETE FROM user_roles;
DELETE FROM votes;
DELETE FROM dishes;
DELETE FROM restaurants;
DELETE FROM users;
ALTER SEQUENCE global_seq RESTART WITH 100000;

INSERT INTO users(name, email, password)
VALUES ('User', 'user@domain.com', '{noop}password'),
       ('Admin', 'admin@domain.com', '{noop}admin');

INSERT INTO user_roles(role, user_id)
VALUES ('USER', 100000),
       ('ADMIN', 100001);

INSERT INTO restaurants(name)
VALUES ('First restaurant'),
       ('Second restaurant');

INSERT INTO dishes(name, price, restaurant_id, date_time)
VALUES ('Dish 1', 100.23, 100002, '2021-01-30 00:00:00'),
       ('Dish 2', 200.64, 100002, '2021-01-31 00:00:00'),
       ('Dish 3', 300.61, 100003, '2021-02-20 00:00:00'),
       ('Dish 4', 400.45, 100003, '2021-01-15 00:00:00');

INSERT INTO votes(user_id, restaurant_id, date_time)
VALUES (100000, 100002, '2021-01-25 02:00:00'),
       (100000, 100002, '2021-01-25 03:00:00'),
       (100000, 100003, '2021-01-25 04:00:00'),
       (100000, 100003, '2021-01-25 05:00:00'),
       (100001, 100002, '2021-01-30 02:00:00'),
       (100001, 100002, '2021-01-30 03:00:00'),
       (100001, 100003, '2021-01-30 04:00:00'),
       (100001, 100003, '2021-01-30 05:00:00'),
       (100001, 100003, '2021-03-31 04:00:00'),
       (100001, 100003, '2021-03-31 05:00:00'),
       (100001, 100003, now()),
       (100000, 100003, '2021-03-31 07:00:00'),
       (100000, 100003, '2021-03-31 08:00:00'),
       (100000, 100003, now());