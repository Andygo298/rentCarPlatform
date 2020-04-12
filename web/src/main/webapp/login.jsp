<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<html>
<head>
    <title>login</title>
    <style>
        div {
            width: 250px;
            text-align: center;
            margin: 0 auto;
        }
        button {
            width: 100px;
            height: 30px;
            background-color: lightcyan;
        }
        input {
            margin-bottom: 10px;
            width: 100%;
        }
        p { color: #8b0f0b }
    </style>
</head>
<body>
<div>
    <h3>Авторизация</h3>
    <form action="${pageContext.request.contextPath}/login" method="post">
        <label for="login">Логин</label><br/>
        <input id="login" type="text" name="login"><br/>
        <label for="password">Пароль</label><br/>
        <input id="password" type="password" name="password"><br/>
        <button class="button" type="submit">Войти</button>
    </form>
    <form method="get" action="<c:url value='/register'/>">
        <button type="submit">Регистрация</button>
    </form>
    <p>${requestScope.error}</p>
    <p>${requestScope.customMessage}</p>
    <p>${requestScope.customMessage2}</p>
</div>
</body>
</html>




