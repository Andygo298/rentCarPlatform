<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<html>
<head>
    <%@include file="bootstrap.jsp" %>
    <title>Service page</title>
</head>
<body>
<a class="btn btn-primary float-right" href="${pageContext.request.contextPath}/logout">logout</a>
<a class="btn btn-primary" href="${pageContext.request.contextPath}/homepage">Back to homepage</a>
<h3>SERVICE</h3>

<form method="get" action="<c:url value='/staff'/>">
    <button type="submit" class="btn btn-primary">Staff list</button>
</form>

<c:if test="${requestScope.cars != null}">
    <table class="table table-striped table-hover table-bordered">
        <tr>
            <c:if test="${sessionScope.authUser.role == 'ADMIN'}">
                <th scope="col">ID</th>
                <th scope="col">Brand</th>
                <th scope="col">Model</th>
                <th scope="col">Type</th>
                <th scope="col">Year</th>
                <th scope="col">Staff</th>
                <th scope="col"></th>
            </c:if>

        </tr>


        <c:forEach items="${requestScope.cars}" var="car">
            <tr>
                <th scope="row">${car.id}</th>
                <td>${car.brand}</td>
                <td>${car.model}</td>
                <td>${car.type}</td>
                <td>${car.year_mfg}</td>
                <td>
                    <c:forEach items="${car.staff}" var="staff">
                        <p>
                            <strong>ID:</strong> ${staff.id}
                            <strong>First name:</strong> ${staff.firstName}
                            <strong>Last name:</strong> ${staff.lastName}
                            <strong>Spec:</strong> ${staff.specialization}
                        </p>
                    </c:forEach>
                </td>
                <td>
                    <button onclick="handleClick(${car.id})" class="btn btn-primary" data-toggle="modal"
                            data-target="#Modal">
                        ADD SPECIALIST
                    </button>
                </td>
            </tr>
        </c:forEach>
    </table>
</c:if>

<nav aria-label="Page navigation">
    <ul class="pagination">
        <c:if test="${requestScope.currentPageService != 1}">
            <li class="page-item"><a class="page-link" href=
                    "${pageContext.request.contextPath}/maintenance?page=${requestScope.currentPageService - 1}">Previous</a>
            </li>
        </c:if>

        <c:forEach begin="1" end="${requestScope.countPagesService}" var="i">
            <c:choose>
                <c:when test="${requestScope.currentPageService eq i}">
                    <li class="page-item"><a class="page-link" href="">${i}</a></li>
                </c:when>
                <c:otherwise>
                    <li class="page-item"><a class="page-link" href=
                            "${pageContext.request.contextPath}/maintenance?page=${i}">${i}</a>
                    </li>
                </c:otherwise>
            </c:choose>
        </c:forEach>

        <c:if test="${requestScope.currentPageService lt requestScope.countPagesService}">
            <li class="page-item"><a class="page-link" href=
                    "${pageContext.request.contextPath}/maintenance?page=${requestScope.currentPageService + 1}">Next</a>
            </li>
        </c:if>
    </ul>
</nav>
<!-- Modal -->
<div class="modal fade" id="Modal" tabindex="-1" role="dialog" aria-labelledby="exampleModalLabel"
     aria-hidden="true">
    <div class="modal-dialog" role="document">
        <form class="modal-content" action="<c:url value='/addSpecialist'/>" method="post">
                <div class="form-group">
                    <label for="FormControlSelect" style="font-weight: bold; font-size: 16px">CHOOSE PERSONS TO ADD</label>
                    <input type="hidden" id="selectedCarId" name="selectedCarId">
                    <select multiple name="specList" class="form-control" id="FormControlSelect">
                        <c:forEach items="${requestScope.staffAll}" var="staff">
                            <option value="${staff.id}">
                                <strong>ID:</strong> ${staff.id}
                                <strong>First name:</strong> ${staff.firstName}
                                <strong>Last name:</strong> ${staff.lastName}
                                <strong>Spec:</strong> ${staff.specialization}
                            </option>
                        </c:forEach>
                    </select>
                </div>
            <div class="modal-footer">
                <button type="button" class="btn btn-secondary" data-dismiss="modal">Close</button>
                <button type="submit" class="btn btn-primary">ADD</button>
            </div>
        </form>
    </div>
</div>

<script>
    function handleClick(id) {
        document.getElementById("selectedCarId").value = id;
    }
</script>
</body>
</html>
