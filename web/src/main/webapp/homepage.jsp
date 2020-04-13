<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<head>
    <%@include file="bootstrap.jsp" %>
    <title>Homepage</title>
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

        .ordersLink {
            position: relative;
        }
        .orderIcon {
            position: absolute;
            display: block;
            width: 30px;
            height: 30px;
            background-color: aquamarine;
            font-weight: bold;
            top: -15px;
            right: -15px;
            text-align: center;
            line-height: 26px;
            border-radius: 50%;
            border: 2px solid #007bff;
            color: #007bff;
        }
    </style>
</head>

<p class="header-panel">
    <a class="ordersLink btn btn-primary" href="${pageContext.request.contextPath}/orders">
            <c:if test="${sessionScope.authUser.role eq 'ADMIN'}">
                ORDERS
            </c:if>
            <c:if test="${sessionScope.authUser.role eq 'USER'}">
                MY ORDERS
            </c:if>
        <span class="orderIcon">${requestScope.activeOrders}</span>
    </a>
    <a class="btn btn-primary float-right" href="${pageContext.request.contextPath}/logout">LOGOUT</a>
</p>
<c:if test="${sessionScope.authUser.role eq 'ADMIN'}">
    <form method="get" action="<c:url value='/addNewCar'/>">
    <button type="submit" class="btn btn-primary">Add NEW CAR</button>
    </form>
</c:if>
    <h3>Car list</h3>
    <c:if test="${requestScope.cars != null}">
        <table class="table table-striped table-hover table-bordered">
            <tr>
                <c:if test="${sessionScope.authUser.role == 'ADMIN'}">
                    <th  scope="col">ID</th>
                </c:if>
                <th scope="col">Image</th>
                <th scope="col">Type</th>
                <th scope="col">Model</th>
                <th scope="col">Brand</th>
                <th scope="col">Year</th>
                <th scope="col">Day Price</th>
                <th scope="col">Available</th>
                <c:if test="${sessionScope.authUser.role == 'USER'}">
                    <th scope="col"></th>
                </c:if>
                <c:if test="${sessionScope.authUser.role == 'ADMIN'}">
                    <th scope="col"></th>
                    <th scope="col"></th>
                </c:if>

            </tr>
            <c:forEach items="${requestScope.cars}" var="car">
                <tr>
                    <c:if test="${sessionScope.authUser.role == 'ADMIN'}">
                    <th scope="row">${car.id}</th>
                    </c:if>
                    <td><img src="${car.img_url}" alt="" width="100"></td>
                    <td>${car.brand}</td>
                    <td>${car.model}</td>
                    <td>${car.type}</td>
                    <td>${car.year_mfg}</td>
                    <td>${car.day_price} $</td>
                    <td>
                        <c:if test="${car.is_rent eq false}">
                            <span class="text-success font-weight-bold">YES</span>
                        </c:if>
                        <c:if test="${car.is_rent eq true}">
                            <span class="text-danger font-weight-bold">NO</span>
                        </c:if>
                    </td>
                    <c:if test="${sessionScope.authUser.role eq 'USER'}">
                        <td>
                            <a href="${pageContext.request.contextPath}/makeOrder?id=${car.id}">
                                <button class="btn btn-primary" ${ car.is_rent eq true  ? 'disabled="disabled"' : ''}>
                                    MAKE ORDER
                                </button>
                            </a>
                        </td>
                    </c:if>
                    <c:if test="${sessionScope.authUser.role == 'ADMIN'}">
                        <td>
                            <a class="btn btn-primary" href="${pageContext.request.contextPath}/editCar?id=${car.id}">
                                EDIT CAR
                            </a>
                        </td>
                        <td>
                            <button onclick="handleDeleteClick(${car.id})" class="btn btn-danger" data-toggle="modal" data-target="#exampleModal">
                                DELETE CAR
                            </button>
                        </td>
                    </c:if>
                </tr>
            </c:forEach>
        </table>
    </c:if>

<!-- Modal -->
<div class="modal fade" id="exampleModal" tabindex="-1" role="dialog" aria-labelledby="exampleModalLabel" aria-hidden="true">
    <div class="modal-dialog" role="document">
        <div class="modal-content">
            <div class="modal-body text-center font-weight-bold">
                Are you sure you want to delete this car?
            </div>
            <div class="modal-footer">
                <button type="button" class="btn btn-secondary" data-dismiss="modal">Close</button>
                <a class="deleteLink btn btn-danger"
                    href="${pageContext.request.contextPath}/deleteCar">
                    DELETE CAR
                </a>
            </div>
        </div>
    </div>
</div>

<script>
    function handleDeleteClick(id) {
        const link = document.querySelector(".deleteLink");
        const path = link.getAttribute("href").split("?")[0];
        link.setAttribute('href', path+"?id="+ id);
    }
</script>


