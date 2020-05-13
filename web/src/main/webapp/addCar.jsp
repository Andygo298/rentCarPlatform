<%--
  Created by IntelliJ IDEA.
  User: DELL
  Date: 10.04.2020
  Time: 11:40
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<html>
<head>
    <title>addCarPage</title>
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
<a class="btn btn-primary float-right" href="${pageContext.request.contextPath}/logout">logout</a>
<a class="btn btn-primary" href="${pageContext.request.contextPath}/homepage">Back to homepage</a>
<h3>ADD NEW CAR</h3>
<form action="${pageContext.request.contextPath}/addNewCar" method="post">
    <div class="form-group row">
        <label class="col-sm-2 col-form-label" for="brand">Brand name</label>
        <div class="col-sm-6">
            <input type="text" class="form-control" id="brand" name="brand" required>
        </div>
    </div>
    <div class="form-group row">
        <label class="col-sm-2 col-form-label" for="model">Model name</label>
        <div class="col-sm-6">
            <input type="text" class="form-control" id="model" name="model" required>
        </div>
    </div>
    <div class="form-group row">
        <label class="col-sm-2 col-form-label" for="type">Car type</label>
        <div class="col-sm-6">
            <input type="text" class="form-control" id="type" name="type" required>
        </div>
    </div>
    <div class="form-group row">
        <label class="col-sm-2 col-form-label" for="year_mfg">Year mfg.</label>
        <div class="col-sm-6">
            <input type="number" class="form-control" id="year_mfg" name="year_mfg" required>
        </div>
    </div>
    <div class="form-group row">
        <label class="col-sm-2 col-form-label" for="day_price">Day price</label>
        <div class="col-sm-6">
            <input type="number" class="form-control" id="day_price" name="day_price" value="0.0" required>
        </div>
    </div>

    <a class="btn btn-warning" href="${pageContext.request.contextPath}/homepage">CANCEL</a>
    <button class="btn btn-success" type="submit">ADD CAR</button>
</form>
</body>
</html>
