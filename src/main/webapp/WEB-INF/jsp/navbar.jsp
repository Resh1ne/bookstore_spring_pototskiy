<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<ul class="navbar">
<body>
    <nav>
        <ul>
            <li><a class="navbar_style" href="/" onclick="navigate('home')">Home</a></li>
            <li><a class="navbar_style" href="/books" onclick="navigate('allBooks')">All Books</a></li>
            <li><a class="navbar_style" href="/users" onclick="navigate('allUsers')">All Users</a></li>
            <li><a class="navbar_style" href="/users/create" onclick="navigate('allUsers')">Create User</a></li>
            <li><a class="navbar_style" href="/books/create" onclick="navigate('allBooks')">Create Book</a></li>
            <li><a class="navbar_style" href="/orders" onclick="navigate('allOrders')">Ord</a></li>
        </ul>
    </nav>
</body>