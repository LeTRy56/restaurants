<%--
  Created by IntelliJ IDEA.
  User: LeTRy
  Date: 4/13/2021
  Time: 3:28 PM
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>

<header>
<%--    <a href="https://letry.ru">Home</a>--%>
    <a href="https://letry.ru"><input type="button" value="Home"></a>
    <a href="${pageContext.request.contextPath}/"><input type="button" value="Restaurants"></a>
    <a href="${pageContext.request.contextPath}/logout"><input type="button" value="Logout"></a>
    <hr>
</header>