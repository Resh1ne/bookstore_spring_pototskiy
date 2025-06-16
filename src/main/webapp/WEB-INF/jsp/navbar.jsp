<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<body>
    <nav>
        <ul>
            <li><a class="navbar_style" href="/" onclick="navigate('home')">Home</a></li>
            <li><a class="navbar_style" href="/books" onclick="navigate('allBooks')">All Books</a></li>

            <!-- Показывать только ADMIN -->
            <c:if test="${sessionScope.user.role == 'ADMIN'}">
                <li><a class="navbar_style" href="/users" onclick="navigate('allUsers')">All Users</a></li>
                <li><a class="navbar_style" href="/users/create" onclick="navigate('allUsers')">Create User</a></li>
            </c:if>

            <!-- Показывать всем авторизованным -->
            <li><a class="navbar_style" href="/logout" onclick="navigate('home')">Logout</a></li>
            <li><a class="navbar_style" href="/orders/my" onclick="navigate('myOrder')">My Order</a></li>

            <!-- Показывать ADMIN и MANAGER -->
            <c:if test="${sessionScope.user.role == 'ADMIN' || sessionScope.user.role == 'MANAGER'}">
                <li><a class="navbar_style" href="/books/create" onclick="navigate('allBooks')">Create Book</a></li>
            </c:if>

            <!-- Показывать ADMIN и MANAGER -->
            <c:if test="${sessionScope.user.role == 'ADMIN' || sessionScope.user.role == 'MANAGER'}">
                <li><a class="navbar_style" href="/orders" onclick="navigate('allOrders')">Orders</a></li>
            </c:if>
        </ul>
    </nav>
</body>