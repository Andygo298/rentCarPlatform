<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<html>
<head>
    <title>Create staff</title>
    <%@include file="bootstrap.jsp" %>
    <style>
        body {
            padding: 20px;
        }

        h3 {
            margin: 20px 0;
        }
    </style>
</head>
<body>
<a class="btn btn-primary float-right" href="${pageContext.request.contextPath}/logout">logout</a>
<a class="btn btn-primary" href="${pageContext.request.contextPath}/staff">Back to staff</a>
<h3>Create new specialist</h3>
<form action="${pageContext.request.contextPath}/createStaff" method="post">
    <div class="form-group row">
        <label class="col-sm-2 col-form-label" for="first_name">First name:</label>
        <div class="col-sm-6">
            <input type="text" class="form-control" id="first_name" name="first_name" required>
        </div>
    </div>
    <div class="form-group row">
        <label class="col-sm-2 col-form-label" for="last_name">Last name:</label>
        <div class="col-sm-6">
            <input type="text" class="form-control" id="last_name" name="last_name" required>
        </div>
    </div>
    <div class="form-group row">
        <label class="col-sm-2 col-form-label" for="specialization">Spec select:</label>
        <div class="col-sm-6">
        <select class="form-control" id="specialization" name="specialization" required>
            <option>Driver</option>
            <option>Mechanic</option>
            <option>Cleaner</option>
        </select>
        </div>
    </div>
    <a class="btn btn-warning" href="${pageContext.request.contextPath}/staff">CANCEL</a>
    <button class="btn btn-success" type="submit">Create</button>
</form>
</body>
</html>
