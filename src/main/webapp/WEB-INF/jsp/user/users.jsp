<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<head>
    <title>Users Management</title>
    <link href="/css/style.css" rel="stylesheet">
    <link href="/css/tables.css" rel="stylesheet">
    <link href="/css/pagination.css" rel="stylesheet">
</head>
<body>
    <jsp:include page="../navbar.jsp" />

    <h1>Users List</h1>

    <table class="data-table">
        <thead>
            <tr>
                <th>#</th>
                <th>ID</th>
                <th>Email</th>
                <th>First Name</th>
                <th>Last Name</th>
                <th>Actions</th>
            </tr>
        </thead>
        <tbody>
            <c:forEach items="${users}" var="user" varStatus="counter">
                <tr>
                    <td>${counter.count + (page.number * page.size)}</td>
                    <td>${user.id}</td>
                    <td><a href="users/${user.id}">${user.email}</a></td>
                    <td>${user.firstName}</td>
                    <td>${user.lastName}</td>
                    <td>
                        <a href="users/${user.id}" class="action-link">View</a>
                        <a href="users/edit/${user.id}" class="action-link">Edit</a>
                        <c:if test="${user.id ne sessionScope.user.id}">
                            <form action="users/delete/${user.id}" method="post" class="delete-form">
                                <button type="submit" class="delete-btn"
                                        onclick="return confirm('Are you sure you want to delete this user?')">
                                    Delete
                                </button>
                            </form>
                        </c:if>
                    </td>
                </tr>
            </c:forEach>
        </tbody>
    </table>

    <div class="pagination-container">
        <div class="pagination">
            <c:if test="${page.hasPrevious()}">
                <a href="?page=${page.number}">Previous</a>
            </c:if>

            <c:forEach begin="1" end="${page.totalPages}" var="i">
                <a href="?page=${i}" class="${i-1 == page.number ? 'current' : ''}">${i}</a>
            </c:forEach>

            <c:if test="${page.hasNext()}">
                <a href="?page=${page.number + 2}">Next</a>
            </c:if>
        </div>
        <div class="page-info">
            Page ${page.number + 1} of ${page.totalPages} | Total users: ${page.totalElements}
        </div>
    </div>
</body>
</html>