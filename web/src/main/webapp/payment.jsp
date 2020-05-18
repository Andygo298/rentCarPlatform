<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<html>
<head>
    <title>Payment page</title>
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
<h3>PAYMENT</h3>
<form action="${pageContext.request.contextPath}/payment?orderId=${requestScope.orderId}" method="post">
    <div class="form-group row">
        <label for="cardNum" class="col-sm-2 col-form-label">Input card number</label>
        <div class="col-sm-6">
            <input type="text" class="form-control" id="cardNum" name="cardNum" required>
        </div>
    </div>
    <div class="form-group row">
        <label for="orderPrice" class="col-sm-2 col-form-label">Sum payment</label>
        <div class="col-sm-6">
            <input type="number" class="form-control" id="orderPrice" name="orderPrice"  value="${requestScope.orderPrice}" readonly>
        </div>
    </div>
    <a class="btn btn-warning" href="${pageContext.request.contextPath}/orders">CANCEL</a>
    <button class="btn btn-success" type="submit">PAY</button>
</form>
</body>
</html>
