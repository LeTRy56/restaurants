<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%--
  Created by IntelliJ IDEA.
  User: LeTRy
  Date: 14.03.2021
  Time: 17:01
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Restaurants</title>
    <link rel="stylesheet" href="css/style.css">
</head>
<body>
<section>
    <form action="index.jsp">
        <input type="submit" value="Home" />
    </form>
    <hr/>
</section>

<section>
    <h3>Choice of restaurant</h3>
    <p></p>
    <jsp:useBean id="user" type="ru.letry.restaurants.dto.UserDTO" scope="request"/>
    You voted for the restaurant today: ${user.restaurantName}
    <p></p>
    <form method="post" action="restaurants">
        <input type="text" name="restaurantName" value="New restaurant">
        <button type="submit">Add restaurant</button>
    </form>
    <br>
    <c:forEach items="${restaurants}" var="restaurant">
        <jsp:useBean id="restaurant" type="ru.letry.restaurants.dto.RestaurantDTO"/>
        <p></p>
        <h3>${restaurant.name}</h3>
        <a href="restaurants?action=vote&restaurantId=${restaurant.id}"><input type="button" value="Vote"></a>
        <a href="restaurants?action=update&restaurantId=${restaurant.id}"><input type="button" value="Update"></a>
        <a href="restaurants?action=delete&restaurantId=${restaurant.id}"><input type="button" value="Delete"></a>
        <p></p>
        Votes: ${restaurant.votes}
        <table border="1" cellpadding="8" cellspacing="0">
            <thead>
            <tr>
                <th>Lunch</th>
                <th>Price</th>
            </tr>
            </thead>
        <c:forEach items="${restaurant.dishes}" var="dish">
            <jsp:useBean id="dish" type="ru.letry.restaurants.model.Dish"/>
            <tr>
                <td>${dish.name}</td>
                <td>${dish.price}</td>
            </tr>
        </c:forEach>
        </table>
    </c:forEach>
</section>
</body>
</html>
