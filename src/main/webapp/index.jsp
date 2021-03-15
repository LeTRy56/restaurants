<%--
  Created by IntelliJ IDEA.
  User: LeTRy
  Date: 14.03.2021
  Time: 17:12
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Home</title>
</head>
<body>
<section>
        <form action="index.jsp">
            <input type="submit" value="Home" />
        </form>
    <hr/>
</section>
<h3><a href="srs.jsp">Software Requirements Specification</a> </h3>
<h3><a href="https://github.com/LeTRy56/restaurants">Source code</a> </h3>
<p></p>
<form action="users" method="post">
    <b>Select user:</b>
    <select name="userId">
        <option value="100000">User</option>
        <option value="100001">Admin</option>
    </select>
    <button type="submit">Select</button>
</form>
</body>
</html>