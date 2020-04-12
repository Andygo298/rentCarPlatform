<%@ page contentType="text/html;charset=UTF-8" language="java" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>


<a href="${pageContext.request.contextPath}/logout">logout</a>
<h3>Cтуденты</h3>
<c:if test="${requestScope.students != null}">
    <table>
        <tr>
            <th>Имя</th>
            <th>фамилия</th>
            <th>email</th>
            <th>blocked</th>
        </tr>
        <c:forEach items="${requestScope.students}" var="student">
            <tr>
                <td>${student.firstName}</td>
                <td>${student.lastName}</td>
                <td>${student.email}</td>
                <td>${student.blocked}</td>
            </tr>
        </c:forEach>
    </table>
</c:if>


<c:if test="${sessionScope.authUser.role == 'ADMIN'}">
    <h3>Добавить студента </h3>
    <form action="${pageContext.request.contextPath}/student" method="post">
        <label for="login">Логин</label>
        <input id="login" type="text" name="login"><br/>

        <label for="password">Пароль</label>
        <input id="password" type="password" name="password"><br/>

        <label for="firstName">имя</label>
        <input id="firstName" type="text" name="firstName"><br/>

        <label for="lastName">фамилия</label>
        <input id="lastName" type="text" name="lastName"><br/>

        <label for="email">email</label>
        <input id="email" type="text" name="email"><br/>

        <label for="phone">phone</label>
        <input id="phone" type="text" name="phone"><br/>

        <input type="submit">
    </form>
</c:if>

