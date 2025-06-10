<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
  <head>
    <title>Order</title>
    <link href="css/style.css" rel="stylesheet" type="text/css" />
  </head>
  <body>
    <jsp:include page="../navbar.jsp" />
    <h1>Order</h1>
    <p>Id: ${order.id}</p>
    <p>User email: ${order.user.email}</p>
    <p>Total cost: ${order.totalCost}</p>
    <p>Status: ${order.status}</p>
    <ul>
      <c:forEach var="item" items="${order.items}">
        <li>
          ${item.book.title} -
          Quantity: ${item.quantity},
          Price: ${item.price},
          Total: ${item.quantity * item.price}
        </li>
      </c:forEach>
    </ul>
  </body>
</html>
