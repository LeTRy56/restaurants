<%--
  Created by IntelliJ IDEA.
  User: LeTRy
  Date: 5/3/2021
  Time: 12:25 PM
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<html>
<jsp:include page="fragments/headTag.jsp"/>
<head>
    <a href="https://letry.ru"><input type="button" value="Home"></a>
</head>
<body>
<%
    String str = request.getParameter("error");
    boolean error = str != null && str.equals("true");
%>
<p></p>
<form id="login_form" action="${pageContext.request.contextPath}/spring_security_check" method="post">
    <input type="text" placeholder="Email" name="username">
    <input type="password" placeholder="Password" name="password">
    <button type="submit">Log in</button>
</form>
<%= error ? "Bad credentials, try again please." : "" %>
<p></p>
<form id="login_form" action="${pageContext.request.contextPath}/spring_security_check" method="post">
    <input type="hidden" value="user@domain.com" name="username">
    <input type="hidden" value="password" name="password">
    <button type="submit">Log in as User</button>
</form>
<form id="login_form" action="${pageContext.request.contextPath}/spring_security_check" method="post">
    <input type="hidden" value="admin@domain.com" name="username">
    <input type="hidden" value="admin" name="password">
    <button type="submit">Log in as Administrator</button>
</form>
<jsp:include page="fragments/footer.jsp"/>
</body>
</html>
