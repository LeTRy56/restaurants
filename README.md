Choice of Restaurant for lunch
==============================

>[Deployed Java web application on the VPS](https://restaurants.letry.ru)

Software Requirements Specification:
-----------------------------------

Design and implement a REST API using Hibernate/Spring/SpringMVC (or Spring-Boot) **without frontend**.

The task is:

Build a voting system for deciding where to have lunch.

2 types of users: admin and regular users
Admin can input a restaurant and it's lunch menu of the day (2-5 items usually, just a dish name and price)
Menu changes each day (admins do the updates)
Users can vote on which restaurant they want to have lunch at
Only one vote counted per user
If user votes again the same day:
If it is before 11:00 we assume that he changed his mind.
If it is after 11:00 then it is too late, vote can't be changed
Each restaurant provides a new menu each day.

As a result, provide a link to github repository. It should contain the code, README.md with API documentation and couple curl commands to test it.

P.S.: Make sure everything works with latest version that is on github :)

P.P.S.: Assume that your API will be used by a frontend developer to build frontend on top of that.




curl samples
-------------
> For Windows use `Git Bash`

#### get All Users
`curl -s http://localhost:8080/rest/admin/users --user admin@domain.com:admin`

#### create new User by Admin
`curl -s -i -X POST -d '{"name": "New","email": "new@domain.com","password": "passwordNew","roles": ["USER"]}' -H 'Content-Type:application/json;charset=UTF-8' http://localhost:8080/rest/admin/users --user admin@domain.com:admin`

#### get User by Admin
`curl -s http://localhost:8080/rest/admin/users/100000 --user admin@domain.com:admin`

#### update User by Admin
`curl -s -i -X PUT -d '{"name": "UserUpdated","email": "userupdated@domain.com","password": "passwordNew","roles": ["USER"]}' -H 'Content-Type:application/json;charset=UTF-8' http://localhost:8080/rest/admin/users/100000 --user admin@domain.com:admin`

#### delete User by Admin
`curl -s -X DELETE http://localhost:8080/rest/admin/users/100000 --user admin@domain.com:admin`

#### get User profile
`curl -s http://localhost:8080/rest/profile --user user@domain.com:password`

#### update User profile
`curl -s -i -X PUT -d '{"name": "UserUpdated","email": "userupdated@domain.com","password": "passwordNew","roles": ["USER"]}' -H 'Content-Type:application/json;charset=UTF-8' http://localhost:8080/rest/profile --user user@domain.com:password`

#### delete User
`curl -s -X DELETE http://localhost:8080/rest/profile --user user@domain.com:password`

#### get All restaurants
`curl -s http://localhost:8080/rest/restaurants --user user@domain.com:password`

#### get Restaurant 100003
`curl -s http://localhost:8080/rest/restaurants/100003 --user user@domain.com:password`

#### vote for Restaurant
`curl -s -i -X POST http://localhost:8080/rest/restaurants/100003/vote --user user@domain.com:password`

#### create new Restaurant by Admin
`curl -s -i -X POST -d '{"name": "New restaurant","dishes":[{"name": "New Dish 1","price": 400.45,"date": "2021-01-15"},{"name": "New Dish 2","price": 300.61,"date": "2021-02-20"}]}' -H 'Content-Type:application/json;charset=UTF-8' http://localhost:8080/rest/admin/restaurants --user admin@domain.com:admin`

#### update Restaurant by Admin
`curl -s -i -X PUT -d '{"id": 100003,"name": "Second Updated restaurant","dishes":[{"id": 100007,"name": "Dish 4 updated","price": 400.45,"date": "2021-01-15"},{"id": 100006,"name": "Dish 3 updated","price": 300.61,"date": "2021-02-20"},{"name": "New Dish","price": 123.23,"date": "2021-02-20"}]}' -H 'Content-Type:application/json;charset=UTF-8' http://localhost:8080/rest/admin/restaurants/100003 --user admin@domain.com:admin`

#### delete Restaurant by Admin
`curl -s -X DELETE http://localhost:8080/rest/admin/restaurants/100003 --user admin@domain.com:admin`

#### create Dish by Admin
`curl -s -i -X POST -d '{"name": "New Dish 123","price": 1111.23,"date": "2021-02-20"}' -H 'Content-Type:application/json;charset=UTF-8' http://localhost:8080/rest/admin/restaurants/100003 --user admin@domain.com:admin`

#### delete Dish by Admin
`curl -s -X DELETE http://localhost:8080/rest/admin/restaurants/100003/dishes/100006 --user admin@domain.com:admin`