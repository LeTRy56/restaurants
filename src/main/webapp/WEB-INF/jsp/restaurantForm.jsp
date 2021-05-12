<%@ page import="java.time.LocalDate" %><%--
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
<%
    String date = request.getParameter("date");
    if (date == null)
        date = LocalDate.now().toString();
%>
<section>
    <jsp:useBean id="restaurant" type="ru.letry.restaurants.model.Restaurant" scope="request"/>
    <h2>${param.action == 'create' ? 'Create restaurant' : 'Edit restaurant '}</h2>
    <form method="get" action="/admin/restaurants/${restaurant.id}/update">
        <input type="date" name="date" value="<%=date%>">
        <button type="submit" class="button-simple">Select</button>
        <p></p>
    </form>
    <form method="post" action="${pageContext.request.contextPath}/admin/restaurants">
        <input type="hidden" name="id" value="${restaurant.id}">
        <input type="hidden" name="date" value="<%=date%>">
        Restaurant:
            <label>
                <input type="text" value="${restaurant.name}" name="restaurantName" minlength="2" maxlength="100" required>
            </label>
            <p></p>
            <a href="/admin/restaurants/${restaurant.id}/dishes/create?date=<%=date%>" class="button-simple">Add dish</a>
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
                    <a href="/admin/restaurants/${restaurant.id}/dishes/${dish.id}/delete?date=<%=date%>" class="button-red">Delete</a>
                </dd>
            </dl>
        </c:forEach>
            <p></p>
        <button type="submit" class="button-green">Save</button>
        <a href="${pageContext.request.contextPath}/restaurants" class="button-simple">Cancel</a>
    </form>
</section>
<jsp:include page="fragments/footer.jsp"/>
</body>
</html>