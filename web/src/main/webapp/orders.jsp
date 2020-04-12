<%--
  Created by IntelliJ IDEA.
  User: DELL
  Date: 12.04.2020
  Time: 10:45
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<html>
<head>
    <title>Orders page</title>
</head>
<body>
<a href="${pageContext.request.contextPath}/logout">logout</a><br>
<a href="${pageContext.request.contextPath}/homepage">Back to homepage</a><br>

<h3>Orders list</h3>
<c:if test="${requestScope.orders != null}">
    <table>
        <tr>
            <c:if test="${sessionScope.authUser.role == 'ADMIN'}">
                <th>Order ID</th>
                <th>User Name</th>
                <th>User Passport</th>
            </c:if>
            <th>User Phone</th>
            <th>Period</th>
            <th>Order Price</th>
            <th>Order Status</th>
        </tr>
        <c:forEach items="${requestScope.orders}" var="order">
            <tr>
                <c:if test="${sessionScope.authUser.role eq 'ADMIN'}">
                    <td>${order.orderId}</td>
                    <td>${order.userName}</td>
                    <td>${order.passport}</td>
                </c:if>
                <td>${order.phone}</td>
                <td>From: ${order.startDate}<br>To: ${order.endDate}</td>
                <td>${order.orderPrice}</td>
                <c:if test="${sessionScope.authUser.role eq 'USER'}">
                    <td>
                        <c:choose>
                            <c:when test="${order.orderStatus eq 'ACCEPTED'}">
                                <a href="${pageContext.request.contextPath}/payment?orderId=${order.orderId}">PAYMENT</a>
                            </c:when>
                            <c:otherwise>
                                ${order.orderStatus}
                            </c:otherwise>
                        </c:choose>
                    </td>
                </c:if>
                <c:if test="${sessionScope.authUser.role eq 'ADMIN'}">
                    <td>
                    <c:choose>
                        <c:when test="${order.orderStatus eq 'IN_PROGRESS'}">
                            <a href="${pageContext.request.contextPath}/approveOrder?orderId=${order.orderId}">APPROVE</a><br>
                            <a href="${pageContext.request.contextPath}/rejectOrder?orderId=${order.orderId}">REJECT</a>
                        </c:when>
                        <c:otherwise>
                            ${order.orderStatus}
                        </c:otherwise>
                    </c:choose>
                    </td>
                </c:if>
            </tr>
        </c:forEach>
    </table>
</c:if>
</body>
</html>
