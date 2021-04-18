<%--
  Created by IntelliJ IDEA.
  User: LeTRy
  Date: 14.03.2021
  Time: 17:12
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<jsp:include page="fragments/headTag.jsp"/>
<body>
<jsp:include page="fragments/bodyHeader.jsp"/>
<br>
<section>
    <form action="users" method="post">
        <b>Select user:</b>
        <select name="id">
            <option value="100000">User</option>
            <option value="100001">Admin</option>
        </select>
        <button type="submit">Select</button>
    </form>
</section>
<jsp:include page="fragments/footer.jsp"/>
</body>
</html>