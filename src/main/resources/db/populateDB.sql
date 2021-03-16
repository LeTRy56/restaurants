DELETE FROM user_roles;
DELETE FROM dishes;
DELETE FROM restaurants;
DELETE FROM users;
ALTER SEQUENCE global_seq RESTART WITH 100000;

INSERT INTO users(name, email, password)
VALUES ('User', 'user@domain.com', 'password'),
       ('Admin', 'admin@doman.com', 'admin');

INSERT INTO user_roles(role, user_id)
VALUES ('USER', 100000),
       ('ADMIN', 100001);

INSERT INTO restaurants(name)
VALUES ('First restaurant'),
       ('Second restaurant');

INSERT INTO dishes(name, price, restaurant_id)
VALUES ('Dish 1', 100.23, 100002),
       ('Dish 2', 200.6423, 100002),
       ('Dish 3', 300.6124, 100003),
       ('Dish 4', 400.457, 100003);
