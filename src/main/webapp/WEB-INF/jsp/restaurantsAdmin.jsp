<%--
  Created by IntelliJ IDEA.
  User: LeTRy
  Date: 14.03.2021
  Time: 17:01
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="https://restaurants.letry.ru/functions" %>
<html>
<jsp:include page="fragments/headTag.jsp"/>
<body>
<jsp:include page="fragments/bodyHeader.jsp"/>
<section>
    <h3>Choice of restaurant</h3>
    <p></p>
    <jsp:useBean id="user" type="ru.letry.restaurants.dto.UserDTO" scope="request"/>
    You can vote from 0:00 to 11:00 AM.
    <p></p>
    Server time: ${fn:formatDateTime(serverTime)}
    <p></p>
    ${user.votedForRestaurant}
    <p></p>
    <form method="post" action="${pageContext.request.contextPath}/admin/restaurants">
        <input type="text" name="restaurantName" value="New restaurant" minlength="2" maxlength="100" required>
        <button type="submit">Add restaurant</button>
    </form>
    <br>

        <table border="1" cellpadding="8" cellspacing="0">
            <thead>
            <tr>
                <th>Restaurant</th>
                <th>Lunch | Price</th>
                <th>Votes</th>
                <th></th>
                <th></th>
                <th></th>
            </tr>
            </thead>

            <c:forEach items="${restaurants}" var="restaurant">
            <jsp:useBean id="restaurant" type="ru.letry.restaurants.dto.RestaurantDTO" />

            <tr>
                <th>${restaurant.name}</th>
                <th>
                    <c:forEach items="${restaurant.dishes}" var="dish">
                        <jsp:useBean id="dish" type="ru.letry.restaurants.model.Dish"/>
                        ${dish.name} | ${dish.price}
                        <p></p>
                    </c:forEach>
                </th>
                <th>${restaurant.votes}</th>
                <th><a href="restaurants/${restaurant.id}/vote"><input type="button" value="Vote"></a></th>
                <th><a href="admin/restaurants/${restaurant.id}/update"><input type="button" value="Update"></a></th>
                <th><a href="admin/restaurants/${restaurant.id}/delete"><input type="button" value="Delete"></a></th>
            </tr>
            </c:forEach>
        </table>
</section>
<jsp:include page="fragments/footer.jsp"/>
</body>
</html>