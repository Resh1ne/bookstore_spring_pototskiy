<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
<head>
    <title>My Order</title>
    <link href="/css/style.css" rel="stylesheet">
    <style>
        .order-container {
            margin: 20px;
            padding: 20px;
            border: 1px solid #ddd;
        }
        .order-item {
            margin: 10px 0;
            padding: 10px;
            background-color: #f9f9f9;
        }
        .catalog-btn, .pay-btn {
            display: inline-block;
            margin-top: 15px;
            padding: 8px 15px;
            color: white;
            text-decoration: none;
            border-radius: 4px;
            margin-right: 10px;
        }
        .catalog-btn {
            background-color: #4CAF50;
        }
        .pay-btn {
            background-color: #2196F3;
        }
        .catalog-btn:hover {
            background-color: #45a049;
        }
        .pay-btn:hover {
            background-color: #0b7dda;
        }
    </style>
</head>
<body>
    <jsp:include page="../navbar.jsp" />
    <h1>My Current Order</h1>

    <c:choose>
        <c:when test="${not empty order}">
            <div class="order-container">
                <p>Order ID: ${order.id}</p>
                <p>Status: ${order.status}</p>
                <p>Total Cost: $${order.totalCost}</p>

                <h3>Items:</h3>
                <c:forEach items="${order.items}" var="item">
                    <div class="order-item">
                        <p>Book: ${item.book.title}</p>
                        <p>Price: $${item.price}</p>
                        <p>Quantity: ${item.quantity}</p>
                    </div>
                </c:forEach>

                <c:if test="${order.status == 'PENDING'}">
                    <form action="/orders/${order.id}/pay" method="post" style="display: inline;">
                        <button type="submit" class="pay-btn">Pay Now</button>
                    </form>
                </c:if>
                <a href="/books" class="catalog-btn">Back to Catalog</a>
            </div>
        </c:when>
    </c:choose>
</body>
</html>