<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%--
  Created by IntelliJ IDEA.
  User: LeTRy
  Date: 15.03.2021
  Time: 20:20
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Restaurant</title>
</head>
<body>
<section>
    <form action="index.jsp">
        <input type="submit" value="Home" />
    </form>
    <hr/>
</section>

<section>
    <h2>${param.action == 'create' ? 'Create restaurant' : 'Edit restaurant'}</h2>
    <jsp:useBean id="restaurant" type="ru.letry.restaurants.model.Restaurant" scope="request"/>
    <form method="post" action="restaurants">
        <input type="hidden" name="restaurantId" value="${restaurant.id}">
        <dl>
            <dt>Restaurant:</dt>
            <dd><input type="text" value="${restaurant.name}" name="restaurantName" required></dd>
        </dl>
        <c:forEach items="${restaurant.dishes}" var="dish">
            <jsp:useBean id="dish" type="ru.letry.restaurants.model.Dish"/>
            <dl>
                <dd><input type="hidden" name="dishId" value="${dish.id}"></dd>
                <dd><input type="text" value="${dish.name}" name="dish${dish.id}name" required></dd>
            </dl>
        </c:forEach>
        <button type="submit">Save</button>
        <button onclick="window.history.back()" type="button">Cancel</button>
    </form>
</section>
</body>
</html>
