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
    <a href="restaurants?action=create">Add restaurant</a>
    <table border="1" cellpadding="8" cellspacing="0">
        <thead>
        <tr>
            <th>Restaurant</th>
            <th>Lunch</th>
            <th>Price</th>
        </tr>
        </thead>
        <c:forEach items="${restaurants}" var="restaurant">
            <jsp:useBean id="restaurant" type="ru.letry.restaurants.model.Restaurant"/>
            <tr>
<%--                <td rowspan="${restaurant.dishes.size()}">${restaurant.name}</td>--%>

                <c:forEach items="${restaurant.dishes}" var="dish">
                    <jsp:useBean id="dish" type="ru.letry.restaurants.model.Dish"/>
                    <tr>
                        <td>${restaurant.name}</td>
                        <td>${dish.name}</td>
                        <td>${dish.price}</td>
                    </tr>
                </c:forEach>
            </tr>
        </c:forEach>
    </table>
</section>
</body>
</html>
