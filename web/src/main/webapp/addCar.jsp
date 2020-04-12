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
</head>
<body>
<form action="${pageContext.request.contextPath}/addNewCar" method="post">
    <label for="brand">Brand name</label>
    <input id="brand" type="text" name="brand"><br/>

    <label for="model">Model name</label>
    <input id="model" type="text" name="model"><br/>

    <label for="type">Type car</label>
    <input id="type" type="text" name="type"><br/>

    <label for="year_mfg">Year mfg.</label>
    <input id="year_mfg" type="number" name="year_mfg"><br/>

    <label for="day_price">Day price</label>
    <input id="day_price" type="number" name="day_price" value="0.0"}><br/>

    <input type="submit">
</form>
</body>
</html>
