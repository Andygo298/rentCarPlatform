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
    <%@include file="bootstrap.jsp" %>
    <style>
        body {
            padding: 20px;
        }

        h3 {
            margin: 20px 0;
        }

    </style>
</head>
<body>
<h3 class="font-weight-bold">Order Form</h3>
<form action="${pageContext.request.contextPath}/makeOrder?id=${requestScope.id}" method="post">
    <div class="form-group row">
        <label for="passport" class="col-sm-2 col-form-label">Passport:</label>
        <div class="col-sm-6">
            <input type="text" class="form-control" id="passport" name="passport" required>
        </div>
    </div>
    <div class="form-group row">
        <label for="phone" class="col-sm-2 col-form-label">Phone num.</label>
        <div class="col-sm-6">
            <input type="tel" class="form-control" id="phone" name="phone" required>
        </div>
    </div>
    <div class="form-group row">
        <label for="startDate" class="col-sm-2 col-form-label">Start date</label>
        <div class="col-sm-6">
            <input type="date" class="form-control" id="startDate" name="startDate" required>
        </div>
    </div>
    <div class="form-group row">
        <label for="endDate" class="col-sm-2 col-form-label">End date</label>
        <div class="col-sm-6">
            <input type="date" class="form-control" id="endDate" name="endDate" required>
        </div>
    </div>
    <button type="submit" class="btn btn-primary btn-lg">Make order</button>
    <p class="text-danger font-weight-bold">${requestScope.error}</p>
</form>
<p>${requestScope.errorMessage}</p>
</body>
</html>
