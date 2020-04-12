<%--
  Created by IntelliJ IDEA.
  User: DELL
  Date: 11.04.2020
  Time: 23:03
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<html>
<head>
    <title>Make order page</title>
    <style>
        p {
            color: #8b0f0b;
            font-weight: bold;
        }
    </style>
</head>
<body>
<form action="${pageContext.request.contextPath}/makeOrder?id=${requestScope.id}" method="post">
    <label for="passport">Passport</label>
    <input id="passport" type="text" name="passport" required><br/>

    <label for="phone">Phone num.</label>
    <input id="phone" type="tel" name="phone" required/><br/>

    <label for="startDate">Start date</label>
    <input id="startDate" type="date" name="startDate" required><br/>

    <label for="endDate">End date</label>
    <input id="endDate" type="date" name="endDate" required><br/>

    <button type="submit">Make order</button>
</form>
<p>${requestScope.errorMessage}</p>
</body>
</html>
