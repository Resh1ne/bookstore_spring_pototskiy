<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html>
<head>
    <title>Order Management | Book Haven</title>
    <link href="/css/style.css" rel="stylesheet" type="text/css" />
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
</head>
<body>
    <jsp:include page="../navbar.jsp" />

    <div class="container">
        <div class="header">
            <h1>Order Management</h1>
        </div>

        <table class="data-table">
            <thead>
                <tr>
                    <th>Order ID</th>
                    <th>Total Cost</th>
                    <th>Status</th>
                    <th>Actions</th>
                </tr>
            </thead>
            <tbody>
                <c:forEach items="${orders}" var="order">
                    <tr>
                        <td>#${order.id}</td>
                        <td>$${order.totalCost}</td>
                        <td>
                            <span class="status-badge status-${order.status.toString().toLowerCase()}">
                                ${order.status}
                            </span>
                        </td>
                        <td>
                            <div class="action-buttons">
                                <a href="/orders/${order.id}" class="action-btn view-btn">View Details</a>
                            </div>
                        </td>
                    </tr>
                </c:forEach>
            </tbody>
        </table>
    </div>
</body>
</html>