<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<html>
<head>
    <title>login</title>
    <%@include file="bootstrap.jsp" %>
    <style>
        body {
            padding: 20px;
            background-image: url("https://www.ecronicon.com/promotions/images/abg3.jpg");
            background-size: 100%;
        }

        h3 {
            margin: 20px 0;
        }

        div {
            width: 250px;
            text-align: center;
            margin: 0 auto;
        }
        button {
            width: 150px;
        }
    </style>
</head>
<body>
<div>
    <h3>Sign In</h3>
    <form class="form-group" action="${pageContext.request.contextPath}/login" method="post">
        <label class="col-form-label" for="login">LOGIN</label><br/>
        <input class="form-control" id="login" type="text" name="login"><br/>
        <label class="col-form-label" for="password">PASSWORD</label><br/>
        <input class="form-control" id="password" type="password" name="password"><br/>
        <button class="btn btn-primary btn-lg" type="submit">LOGIN</button>
    </form>
    <form method="get" action="<c:url value='/register'/>">
        <button class="btn btn-warning btn-lg" type="submit">REGISTER</button>
    </form>
    <p class="text-danger font-weight-bold">${requestScope.error}</p>
    <p class="text-success font-weight-bold">${requestScope.customMessage}</p>
    <p class="text-success font-weight-bold">${requestScope.customMessage2}</p>
</div>
</body>
</html>




