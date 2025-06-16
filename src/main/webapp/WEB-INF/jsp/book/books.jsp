<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<head>
    <title>Books Management</title>
    <link href="/css/style.css" rel="stylesheet">
    <link href="/css/tables.css" rel="stylesheet">
    <link href="/css/pagination.css" rel="stylesheet">
</head>
<body>
    <jsp:include page="../navbar.jsp" />

    <h1>Books List</h1>

    <table class="data-table">
        <thead>
            <tr>
                <th>#</th>
                <th>ID</th>
                <th>Title</th>
                <th>Author</th>
                <th>Price</th>
                <th>Actions</th>
            </tr>
        </thead>
        <tbody>
            <c:forEach items="${books}" var="book" varStatus="counter">
                <tr>
                    <td>${counter.count + (page.number * page.size)}</td>
                    <td>${book.id}</td>
                    <td><a href="books/${book.id}"><c:out value="${book.title}"/></a></td>
                    <td><c:out value="${book.author}"/></td>
                    <td>$${book.price}</td>
                    <td>
                        <a href="books/${book.id}" class="action-link">View</a>

                        <!-- Показывать Edit только для ADMIN и MANAGER -->
                        <c:if test="${sessionScope.user.role == 'ADMIN' || sessionScope.user.role == 'MANAGER'}">
                            <a href="books/edit/${book.id}" class="action-link">Edit</a>
                        </c:if>

                        <!-- Показывать Delete только для ADMIN и MANAGER -->
                        <c:if test="${sessionScope.user.role == 'ADMIN' || sessionScope.user.role == 'MANAGER'}">
                            <form action="books/delete/${book.id}" method="post" class="delete-form">
                                <button type="submit" class="delete-btn">Delete</button>
                            </form>
                        </c:if>

                        <form action="orders/add/${book.id}" method="post" style="display: inline;">
                            <button type="submit" class="action-link-button">Add to order</button>
                        </form>
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
            Page ${page.number + 1} of ${page.totalPages} | Total books: ${page.totalElements}
        </div>
    </div>
</body>
</html>