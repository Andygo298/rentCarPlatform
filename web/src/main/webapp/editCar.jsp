<%--
  Created by IntelliJ IDEA.
  User: DELL
  Date: 09.04.2020
  Time: 18:31
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<html>
<head>
    <title>EditCar</title>
</head>
<body>
<c:if test="${requestScope.carInstance != null}">
    <h3>Edit car data </h3>
    <form action="${pageContext.request.contextPath}/editCar?id=${requestScope.carInstance.id}" method="post">
        <label for="brand">Brand</label>
        <input id="brand" type="text" name="brand" value="${requestScope.carInstance.brand}"><br/>

        <label for="model">Model</label>
        <input id="model" type="text" name="model" value="${requestScope.carInstance.model}"/><br/>

        <label for="type">Type</label>
        <input id="type" type="text" name="type" value="${requestScope.carInstance.type}"><br/>

        <label for="year_mfg">Year mfg</label>
        <input id="year_mfg" type="number" name="year_mfg" value="${requestScope.carInstance.year_mfg}"><br/>

        <label for="img_url">img_url</label>
        <input id="img_url" type="text" name="img_url" value="${requestScope.carInstance.img_url}"><br/>

        <label for="day_price">Day price</label>
        <input id="day_price" type="number" name="day_price" value="${requestScope.carInstance.day_price}"><br/>

        <button type="submit">SAVE</button>
    </form>
</c:if>
</body>
</html>
