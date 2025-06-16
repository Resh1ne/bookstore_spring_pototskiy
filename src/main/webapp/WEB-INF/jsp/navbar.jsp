<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<nav>
    <ul>
        <li><a class="navbar_style" href="/" onclick="navigate('home')">Home</a></li>
        <li><a class="navbar_style" href="/books" onclick="navigate('allBooks')">All Books</a></li>

        <!-- ADMIN only -->
        <c:if test="${sessionScope.user.role == 'ADMIN'}">
            <li><a class="navbar_style" href="/users" onclick="navigate('allUsers')">Users</a></li>
            <li><a class="navbar_style" href="/users/create" onclick="navigate('allUsers')">Create User</a></li>
        </c:if>

        <!-- For all logged in users -->
        <li><a class="navbar_style" href="/orders/my" onclick="navigate('myOrder')">My Orders</a></li>
        <li><a class="navbar_style" href="/logout" onclick="navigate('home')">Logout</a></li>

        <!-- ADMIN and MANAGER -->
        <c:if test="${sessionScope.user.role == 'ADMIN' || sessionScope.user.role == 'MANAGER'}">
            <li><a class="navbar_style" href="/books/create" onclick="navigate('allBooks')">Add Book</a></li>
            <li><a class="navbar_style" href="/orders" onclick="navigate('allOrders')">All Orders</a></li>
        </c:if>
    </ul>
</nav>