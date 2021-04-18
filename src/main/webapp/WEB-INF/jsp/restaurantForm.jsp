<%--
  Created by IntelliJ IDEA.
  User: LeTRy
  Date: 15.03.2021
  Time: 20:20
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
<jsp:include page="fragments/headTag.jsp"/>
<body>
<jsp:include page="fragments/bodyHeader.jsp"/>

<section>
    <h2>${param.action == 'create' ? 'Create restaurant' : 'Edit restaurant'}</h2>
    <jsp:useBean id="restaurant" type="ru.letry.restaurants.model.Restaurant" scope="request"/>
    <a href="/admin/restaurants/${restaurant.id}/dishes/create"><input type="button" value="Add dish"></a>
    <form method="post" action="/admin/restaurants">
        <input type="hidden" name="id" value="${restaurant.id}">
        <dt>Restaurant:</dt>
        <dd><label>
            <input type="text" value="${restaurant.name}" name="restaurantName" minlength="2" maxlength="100" required>
        </label></dd>
            <c:forEach items="${restaurant.dishes}" var="dish">
                <jsp:useBean id="dish" type="ru.letry.restaurants.model.Dish"/>
                <dl>
                    <dd>
                        <label>
                            <input type="text" name="dishName${dish.id}" value="${dish.name}" minlength="2" maxlength="100" required>
                        </label>
                        <label>
                            <input type="number" name="dishPrice${dish.id}" value="${dish.price}" min="5" max="5000" step=".01" required>
                        </label>
                        <a href="/admin/restaurants/${restaurant.id}/dishes/${dish.id}/delete"><input type="button" value="Delete"></a>
                    </dd>
                </dl>
            </c:forEach>
        <button type="submit">Save</button>
        <a href="/restaurants"><input type="button" value="Cancel"></a>
    </form>
</section>
<jsp:include page="fragments/footer.jsp"/>
</body>
</html>