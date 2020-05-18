<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<html>
<head>
    <%@include file="bootstrap.jsp" %>
    <title>Staff list</title>
    <style>
        body {
            padding: 20px;
        }

        h3 {
            margin: 20px 0;
        }

        button:disabled {
            opacity: .5;
            cursor: default;
        }

        .modal-body {
            padding: 20px;
            font-size: 20px;
        }

        .table td,
        .table th {
            vertical-align: middle;
            text-align: center;
        }

        .header-panel form {
            display: inline-block;
            margin-right: 15px;
        }

    </style>
</head>
<body>
<div class="header-panel">
<a class="btn btn-primary float-right" href="${pageContext.request.contextPath}/logout">LOGOUT</a>
<a class="btn btn-primary" href="${pageContext.request.contextPath}/maintenance">Back to maintenance</a>
<form method="get" action="<c:url value='/createStaff'/>">
    <button type="submit" class="btn btn-primary">Create new worker</button>
</form>
</div>
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
                    <button onclick="handleDeleteClick(${staff.id})" class="btn btn-danger" data-toggle="modal"
                            data-target="#exampleModal">
                        DELETE WORKER
                    </button>
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

<div class="modal fade" id="exampleModal" tabindex="-1" role="dialog" aria-labelledby="exampleModalLabel"
     aria-hidden="true">
    <div class="modal-dialog" role="document">
        <div class="modal-content">
            <div class="modal-body text-center font-weight-bold">
                Are you sure you want to delete this worker?
            </div>
            <div class="modal-footer">
                <button type="button" class="btn btn-secondary" data-dismiss="modal">Close</button>
                <a class="deleteLink btn btn-danger"
                   href="${pageContext.request.contextPath}/delStaff">
                    DELETE WORKER
                </a>
            </div>
        </div>
    </div>
</div>

<script>
    function handleDeleteClick(id) {
        const link = document.querySelector(".deleteLink");
        const path = link.getAttribute("href").split("?")[0];
        link.setAttribute('href', path + "?id=" + id);
    }
</script>

</body>
</html>
