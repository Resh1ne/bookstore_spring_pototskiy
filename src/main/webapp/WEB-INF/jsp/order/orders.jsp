<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<html>
<head>
    <link rel="stylesheet" type="text/css" href="/css/style.css">
    <title>Orders List</title>
</head>
<body>
<jsp:include page="../navbar.jsp" />
    <h1>Orders List</h1>
    <table>
        <thead>
            <tr>
                <th>ID</th>
                <th>Total Cost</th>
                <th>Status</th>
                <!-- Add more columns as needed -->
                <th>Actions</th>
            </tr>
        </thead>
        <tbody>
            <c:forEach items="${orders}" var="order" varStatus="counter">
                <tr>
                    <td>${order.id}</td>
                    <td>${order.totalCost}</td>
                    <td>${order.status}</td>

                    <td><a href="<c:url value='orders/${order.id}'/>">Details</a></td>
                </tr>
            </c:forEach>
        </tbody>
    </table>
</body>
</html>
