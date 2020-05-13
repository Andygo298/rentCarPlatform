<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<html>
<head>
    <%@include file="bootstrap.jsp" %>
    <title>Staff list</title>
</head>
<body>
<a class="btn btn-primary float-right" href="${pageContext.request.contextPath}/logout">LOGOUT</a>
<a class="btn btn-primary" href="${pageContext.request.contextPath}/maintenance">Back to maintenance</a>
<form method="get" action="<c:url value='/createStaff'/>">
    <button type="submit" class="btn btn-primary">Create new worker</button>
</form>
<h3>Staff list</h3>
<c:if test="${applicationScope.staff != null}">
    <table class="table table-striped table-hover table-bordered">
        <tr>
            <c:if test="${sessionScope.authUser.role == 'ADMIN'}">
                <th scope="col">Id</th>
                <th scope="col">First name</th>
                <th scope="col">Last name</th>
                <th scope="col">Spec</th>
                <th scope="col"></th>
                <th scope="col"></th>
            </c:if>

        </tr>


        <c:forEach items="${applicationScope.staff}" var="staff">
            <tr>
                <th scope="row">${staff.id}</th>
                <td>${staff.firstName}</td>
                <td>${staff.lastName}</td>
                <td>${staff.specialization}</td>
                <td>
                    <a class="btn btn-primary" href="${pageContext.request.contextPath}/editStaff?id=${staff.id}">
                        EDIT INFO
                    </a>
                </td>
                <td>
                    <a class="btn btn-primary" href="${pageContext.request.contextPath}/delStaff?id=${staff.id}">
                        DELETE WORKER
                    </a>
                </td>
            </tr>
        </c:forEach>
    </table>
</c:if>
<nav aria-label="Page navigation">
    <ul class="pagination">
        <c:if test="${applicationScope.currentPageStaff != 1}">
            <li class="page-item"><a class="page-link" href=
                    "${pageContext.request.contextPath}/staff?page=${applicationScope.currentPageStaff - 1}">Previous</a>
            </li>
        </c:if>

        <c:forEach begin="1" end="${applicationScope.countPagesStaff}" var="i">
            <c:choose>
                <c:when test="${applicationScope.currentPageStaff eq i}">
                    <li class="page-item"><a class="page-link" href="">${i}</a></li>
                </c:when>
                <c:otherwise>
                    <li class="page-item"><a class="page-link" href=
                            "${pageContext.request.contextPath}/staff?page=${i}">${i}</a>
                    </li>
                </c:otherwise>
            </c:choose>
        </c:forEach>

        <c:if test="${applicationScope.currentPageStaff lt applicationScope.countPagesStaff}">
            <li class="page-item"><a class="page-link" href=
                    "${pageContext.request.contextPath}/staff?page=${applicationScope.currentPageStaff + 1}">Next</a>
            </li>
        </c:if>
    </ul>
</nav>
</body>
</html>
