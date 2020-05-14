<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Register</title>
    <%@include file="bootstrap.jsp" %>
    <style>
        body {
            padding: 20px;
        }

        h3 {
            margin: 20px 0;
        }

        form {
            width: 70%;
            margin: 10px auto;
        }
        .btn-block {
            width: 200px;
            margin: 10px auto;
        }

    </style>
</head>
<body>
<h3 class="text-center">REGISTRATION</h3>
<form action="${pageContext.request.contextPath}/register" method="post">
    <div class="form-group row">
        <label for="login" class="col-sm-2 col-form-label">Login:</label>
        <div class="col-sm-10">
            <input type="text" class="form-control" id="login" name="login">
        </div>
    </div>
    <div class="form-group row">
        <label for="password" class="col-sm-2 col-form-label">Password:</label>
        <div class="col-sm-10">
            <input type="password" class="form-control" id="password" name="password">
        </div>
    </div>
    <div class="form-group row">
        <label for="firstName" class="col-sm-2 col-form-label">First Name:</label>
        <div class="col-sm-10">
            <input type="text" class="form-control" id="firstName" name="firstName">
        </div>
    </div>
    <div class="form-group row">
        <label for="lastName" class="col-sm-2 col-form-label">Last Name:</label>
        <div class="col-sm-10">
            <input type="text" class="form-control" id="lastName" name="lastName">
        </div>
    </div>
    <div class="form-group row">
        <label for="email" class="col-sm-2 col-form-label">Email:</label>
        <div class="col-sm-10">
            <input type="email" class="form-control" id="email" name="email">
        </div>
    </div>
    <div class="form-group row">
        <label for="phone" class="col-sm-2 col-form-label">Phone:</label>
        <div class="col-sm-10">
            <input type="tel" class="form-control" id="phone" name="phone">
        </div>
    </div>
    <button type="submit" class="btn btn-primary btn-lg btn-block">Register</button>
    <p class="text-danger font-weight-bold">${requestScope.error}</p>
</form>
</body>
</html>

