<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<head>
    <title>Homepage</title>
    <style>
        button:disabled {
            opacity: 0.5;
        }
        .ordersLink {
            position: relative;
        }
        .orderIcon {
            position: absolute;
            display: block;
            width: 20px;
            height: 20px;
            background-color: #34ce57;
            font-weight: bold;
            top: -10px;
            text-align: center;
            line-height: 20px;
            border-radius: 50%;
            right: -10px;
        }
    </style>
</head>
<a href="${pageContext.request.contextPath}/logout">logout</a><br>
<p class="header-panel">
    <a class="ordersLink" href="${pageContext.request.contextPath}/orders">
        <button>
            <c:if test="${sessionScope.authUser.role eq 'ADMIN'}">
                ORDERS
            </c:if>
            <c:if test="${sessionScope.authUser.role eq 'USER'}">
                MY ORDERS
            </c:if>
        </button>
        <span class="orderIcon">${requestScope.activeOrders}</span>
    </a>
</p>
<c:if test="${sessionScope.authUser.role eq 'ADMIN'}">
    <form method="get" action="<c:url value='/addNewCar'/>">
    <button type="submit">Add NEW car to base</button>
    </form>
</c:if>
    <h3>Car list</h3>
    <c:if test="${requestScope.cars != null}">
        <table>
            <tr>
                <c:if test="${sessionScope.authUser.role == 'ADMIN'}">
                    <th>ID</th>
                </c:if>
                <th>Image</th>
                <th>Type</th>
                <th>Model</th>
                <th>Brand</th>
                <th>Year_MFG</th>
                <th>DAY_Price</th>
                <th>Is_rent?</th>
                <c:if test="${sessionScope.authUser.role == 'USER'}">
                    <th></th>
                </c:if>
                <c:if test="${sessionScope.authUser.role == 'ADMIN'}">
                    <th></th>
                    <th></th>
                </c:if>

            </tr>
            <c:forEach items="${requestScope.cars}" var="car">
                <tr>
                    <c:if test="${sessionScope.authUser.role == 'ADMIN'}">
                        <td>${car.id}</td>
                    </c:if>
                    <td><img src="${car.img_url}" alt="" width="200"></td>
                    <td>${car.brand}</td>
                    <td>${car.model}</td>
                    <td>${car.type}</td>
                    <td>${car.year_mfg}</td>
                    <td>${car.day_price}</td>
                    <td>${car.is_rent}</td>
                    <c:if test="${sessionScope.authUser.role eq 'USER'}">
                        <td>
                            <a href="${pageContext.request.contextPath}/makeOrder?id=${car.id}">
                                <button  ${ car.is_rent eq true  ? 'disabled="disabled"' : ''}>MAKE ORDER</button>
                            </a>
                        </td>
                    </c:if>
                    <c:if test="${sessionScope.authUser.role == 'ADMIN'}">
                        <td>
                            <a href="${pageContext.request.contextPath}/editCar?id=${car.id}">
                                EDIT CAR
                            </a>
                        </td>
                        <td>
                            <a href="${pageContext.request.contextPath}/deleteCar?id=${car.id}">
                                DELETE CAR
                            </a>
                        </td>
                    </c:if>
                </tr>
            </c:forEach>
        </table>
    </c:if>


