<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<head>
    <title>Order #${order.id} | Book Haven</title>
    <link href="/css/style.css" rel="stylesheet" type="text/css" />
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
</head>
<body>
    <jsp:include page="../navbar.jsp" />

    <div class="order-container">
        <div class="order-header">
            <h1>Order Details</h1>
            <p class="text-muted">Order #${order.id}</p>
        </div>

        <div class="order-summary">
            <div class="order-summary-label">Customer:</div>
            <div class="order-summary-value">${order.user.firstName} ${order.user.lastName} (${order.user.email})</div>

            <div class="order-summary-label">Status:</div>
            <div class="order-summary-value">
                <span class="status-badge status-${order.status.toString().toLowerCase()}">
                    ${order.status}
                </span>
            </div>

            <div class="order-summary-label">Total Cost:</div>
            <div class="order-summary-value">$${order.totalCost}</div>
        </div>

        <div class="order-items">
            <h3>Items</h3>

            <c:forEach items="${order.items}" var="item">
                <div class="order-item">
                    <div class="order-item-info">
                        <div class="order-item-title">${item.book.title}</div>
                        <div class="order-item-details">
                            <span>Author: ${item.book.author}</span>
                            <span>Price: $${item.price}</span>
                            <span>Quantity: ${item.quantity}</span>
                            <span>Total: ${(item.quantity != null ? item.quantity : 0) * (item.price != null ? item.price : 0)}</span>
                        </div>
                    </div>
                </div>
            </c:forEach>
        </div>

        <div class="order-actions">
            <a href="/orders" class="btn btn-secondary">Back to Orders</a>
        </div>
    </div>
</body>
</html>