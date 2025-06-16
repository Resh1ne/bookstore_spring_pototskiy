<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<head>
    <title>My Order | Book Haven</title>
    <link href="/css/style.css" rel="stylesheet" type="text/css" />
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
</head>
<body>
    <jsp:include page="../navbar.jsp" />

    <div class="order-container">
        <div class="order-header">
            <h1>My Current Order</h1>
        </div>

        <c:choose>
            <c:when test="${not empty order}">
                <div class="order-summary">
                    <div class="order-summary-label">Order ID:</div>
                    <div class="order-summary-value">#${order.id}</div>

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
                    <h3>Order Items</h3>

                    <c:forEach items="${order.items}" var="item">
                        <div class="order-item">
                            <div class="order-item-info">
                                <div class="order-item-title">${item.book.title}</div>
                                <div class="order-item-details">
                                    <span>$${item.price} each</span>
                                    <span>× ${item.quantity}</span>
                                </div>
                            </div>

                            <div class="order-item-actions">
                                <form action="/orders/remove/${item.book.id}" method="post">
                                    <button type="submit" class="btn btn-danger">Remove</button>
                                </form>
                            </div>
                        </div>
                    </c:forEach>
                </div>

                <div class="order-actions">
                    <a href="/books" class="btn btn-secondary">Continue Shopping</a>

                    <c:if test="${order.status == 'PENDING' && !order.items.isEmpty()}">
                        <form action="/orders/${order.id}/pay" method="post" style="display: inline;">
                            <button type="submit" class="btn btn-primary">Proceed to Payment</button>
                        </form>
                    </c:if>
                </div>
            </c:when>

            <c:otherwise>
                <div class="empty-state">
                    <div class="empty-state-icon">
                        <i class="fas fa-shopping-cart"></i>
                    </div>
                    <h3>Your order is empty</h3>
                    <p>Start shopping to add items to your order</p>
                    <a href="/books" class="btn btn-primary">Browse Catalog</a>
                </div>
            </c:otherwise>
        </c:choose>
    </div>
</body>
</html>