<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<head>
    <title>User Management | Book Haven</title>
    <link href="/css/style.css" rel="stylesheet" type="text/css" />
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
</head>
<body>
    <jsp:include page="../navbar.jsp" />

    <div class="container">
        <div class="header">
            <h1>User Management</h1>

            <c:if test="${sessionScope.user.role == 'ADMIN'}">
                <a href="/users/create" class="btn btn-primary">Add New User</a>
            </c:if>
        </div>

        <table class="data-table">
            <thead>
                <tr>
                    <th>#</th>
                    <th>ID</th>
                    <th>Name</th>
                    <th>Email</th>
                    <th>Role</th>
                    <th>Actions</th>
                </tr>
            </thead>
            <tbody>
                <c:forEach items="${users}" var="user" varStatus="counter">
                    <tr>
                        <td>${counter.count + (page.number * page.size)}</td>
                        <td>${user.id}</td>
                        <td>${user.firstName} ${user.lastName}</td>
                        <td><a href="/users/${user.id}" class="user-email">${user.email}</a></td>
                        <td>${user.role}</td>
                        <td>
                            <div class="action-buttons">
                                <a href="/users/${user.id}" class="action-btn view-btn">View</a>
                                <a href="/users/edit/${user.id}" class="action-btn edit-btn">Edit</a>

                                <c:if test="${user.id ne sessionScope.user.id && sessionScope.user.role == 'ADMIN'}">
                                    <form action="/users/delete/${user.id}" method="post" style="display: inline;">
                                        <button type="submit" class="action-btn delete-btn"
                                                onclick="return confirm('Are you sure you want to delete this user?')">
                                            Delete
                                        </button>
                                    </form>
                                </c:if>
                            </div>
                        </td>
                    </tr>
                </c:forEach>
            </tbody>
        </table>

        <div class="pagination-container">
            <div class="pagination">
                <c:if test="${page.hasPrevious()}">
                    <a href="?page=${page.number}" class="page-link">Previous</a>
                </c:if>

                <c:forEach begin="1" end="${page.totalPages}" var="i">
                    <a href="?page=${i}" class="page-link ${i-1 == page.number ? 'active' : ''}">${i}</a>
                </c:forEach>

                <c:if test="${page.hasNext()}">
                    <a href="?page=${page.number + 2}" class="page-link">Next</a>
                </c:if>
            </div>
            <div class="page-info">
                Showing ${page.number * page.size + 1} to ${page.number * page.size + page.numberOfElements}
                of ${page.totalElements} users
            </div>
        </div>
    </div>
</body>
</html>