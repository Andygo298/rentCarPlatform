<%--
  Created by IntelliJ IDEA.
  User: DELL
  Date: 12.04.2020
  Time: 18:38
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<html>
<head>
    <title>Payment page</title>
</head>
<body>
<form action="${pageContext.request.contextPath}/payment?orderId=${requestScope.orderId}" method="post">
    <label for="cardNum">Input card number</label>
    <input id="cardNum" type="text" name="cardNum" required><br/>

    <label for="orderPrice">Sum payment</label>
    <input id="orderPrice" type="number" name="orderPrice" value="${requestScope.orderPrice}"/><br/>

    <button type="submit">PAY</button>
</form>
</body>
</html>
