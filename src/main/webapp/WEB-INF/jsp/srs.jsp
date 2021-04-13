<%--
  Created by IntelliJ IDEA.
  User: LeTRy
  Date: 14.03.2021
  Time: 19:53
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<jsp:include page="fragments/headTag.jsp"/>
<body>
<jsp:include page="fragments/bodyHeader.jsp"/>
<section>
    <h3><p>Software Requirements Specification</p></h3>
<p>Design and implement a REST API using Hibernate/Spring/SpringMVC (or Spring-Boot) <strong>without frontend</strong>.</p>
<p>The task is:</p>
<p>Build a voting system for deciding where to have lunch.</p>
<ul>
    <li>2 types of users: admin and regular users</li>
    <li>Admin can input a restaurant and it's lunch menu of the day (2-5 items usually, just a dish name and price)</li>
    <li>Menu changes each day (admins do the updates)</li>
    <li>Users can vote on which restaurant they want to have lunch at</li>
    <li>Only one vote counted per user</li>
    <li>If user votes again the same day:
        <ul>
            <li>If it is before 11:00 we assume that he changed his mind.</li>
            <li>If it is after 11:00 then it is too late, vote can't be changed</li>
        </ul>
    </li>
</ul>
<p>Each restaurant provides a new menu each day.</p>
<p>As a result, provide a link to github repository. It should contain the code, README.md with API documentation and couple curl commands to test it.</p>
<p>P.S.: Make sure everything works with latest version that is on github :)</p>
<p>P.P.S.: Assume that your API will be used by a frontend developer to build frontend on top of that.</p>
</section>
<jsp:include page="fragments/footer.jsp"/>
</body>
</html>
