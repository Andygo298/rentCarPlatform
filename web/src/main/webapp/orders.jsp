<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<html>
<head>
    <title>Orders page</title>
    <%@include file="bootstrap.jsp" %>
    <style>
        body {
            padding: 20px;
        }

        .table td,
        .table th {
            vertical-align: middle;
        }

        h3 {
            margin: 20px 0;
        }

        span { font-weight: bold; }
    </style>
</head>
<body>
<a class="btn btn-primary float-right" href="${pageContext.request.contextPath}/logout">LOGOUT</a>
<a class="btn btn-primary" href="${pageContext.request.contextPath}/homepage">Back to homepage</a>

<h3>Orders List</h3>

<nav aria-label="Page navigation">
    <ul class="pagination">
        <c:if test="${requestScope.currentPageOrder != 1}">
            <li class="page-item" ><a class="page-link" href=
                    "${pageContext.request.contextPath}/orders?page=${requestScope.currentPageOrder - 1}">Previous</a>
            </li>
        </c:if>

        <c:forEach begin="1" end="${requestScope.countPages}" var="i">
            <c:choose>
                <c:when test="${requestScope.currentPageOrder eq i}">
                    <li class="page-item"><a class="page-link" href="">${i}</a></li>
                </c:when>
                <c:otherwise>
                    <li class="page-item"><a class="page-link" href=
                            "${pageContext.request.contextPath}/orders?page=${i}">${i}</a>
                    </li>
                </c:otherwise>
            </c:choose>
        </c:forEach>

        <c:if test="${requestScope.currentPageOrder lt requestScope.countPages}">
            <li class="page-item" ><a class="page-link" href=
                    "${pageContext.request.contextPath}/orders?page=${requestScope.currentPageOrder + 1}">Next</a>
            </li>
        </c:if>
    </ul>
</nav>

<c:if test="${requestScope.orders.size() > 0}">
    <table class="table table-striped table-hover table-bordered">
        <tr>
            <c:if test="${sessionScope.authUser.role == 'ADMIN'}">
                <th scope="col">Order ID</th>
                <th scope="col">User Name</th>
                <th scope="col">User Passport</th>
            </c:if>
            <th scope="col">User Phone</th>
            <th scope="col">Car</th>
            <th scope="col">Period</th>
            <th scope="col">Order Price</th>
            <th scope="col">Order Status</th>
        </tr>
        <c:forEach items="${requestScope.orders}" var="order">
            <tr>
                <c:if test="${sessionScope.authUser.role eq 'ADMIN'}">
                    <th class="text-center" scope="row">${order.orderId}</th>
                    <td>${order.userName}</td>
                    <td>${order.passport}</td>
                </c:if>
                <td>${order.phone}</td>
                <td>${order.carName}</td>
                <td>From: ${order.startDate}<br>To: ${order.endDate}</td>
                <td>${order.orderPrice} $</td>
                <c:if test="${sessionScope.authUser.role eq 'USER'}">
                    <td class="text-center">
                        <c:choose>
                            <c:when test="${order.orderStatus eq 'ACCEPTED'}">
                                <a class="btn btn-warning btn-block"
                                   href="${pageContext.request.contextPath}/payment?orderId=${order.orderId}">
                                    PAYMENT
                                </a>
                            </c:when>
                            <c:when test="${order.orderStatus eq 'REJECTED'}">
                                <span class="text-danger">${order.orderStatus}</span>
                            </c:when>
                            <c:when test="${order.orderStatus eq 'IN_PROGRESS'}">
                                <span class="text-warning">WAITING FOR APPROVE</span>
                            </c:when>
                            <c:otherwise>
                                <span class="text-success">${order.orderStatus}</span>
                            </c:otherwise>
                        </c:choose>
                    </td>
                </c:if>
                <c:if test="${sessionScope.authUser.role eq 'ADMIN'}">
                    <td class="text-center">
                        <c:choose>
                            <c:when test="${order.orderStatus eq 'IN_PROGRESS'}">
                                <a class="btn btn-success btn-block"
                                   href="${pageContext.request.contextPath}/approveOrder?orderId=${order.orderId}">APPROVE</a><br>
                                <a class="btn btn-danger btn-block"
                                   href="${pageContext.request.contextPath}/rejectOrder?orderId=${order.orderId}">REJECT</a>
                            </c:when>
                            <c:when test="${order.orderStatus eq 'REJECTED'}">
                                <span class="text-danger">${order.orderStatus}</span>
                            </c:when>
                            <c:when test="${order.orderStatus eq 'ACCEPTED'}">
                                <span class="text-warning">${order.orderStatus}</span>
                            </c:when>
                            <c:otherwise>
                                <span class="text-success">${order.orderStatus}</span>
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
