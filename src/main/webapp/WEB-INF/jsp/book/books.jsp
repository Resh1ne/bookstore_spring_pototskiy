<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<head>
    <title>Book Catalog | Book Haven</title>
    <link href="/css/style.css" rel="stylesheet" type="text/css" />
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
</head>
<body>
    <jsp:include page="../navbar.jsp" />

    <div class="container">
        <div class="header">
            <h1>Book Catalog</h1>

            <c:if test="${sessionScope.user.role == 'ADMIN' || sessionScope.user.role == 'MANAGER'}">
                <a href="/books/create" class="btn btn-primary">Add New Book</a>
            </c:if>
        </div>

        <table class="data-table">
            <thead>
                <tr>
                    <th>#</th>
                    <th>Cover</th>
                    <th>Title</th>
                    <th>Author</th>
                    <th>Genre</th>
                    <th>Price</th>
                    <th>Actions</th>
                </tr>
            </thead>
            <tbody>
                <c:forEach items="${books}" var="book" varStatus="counter">
                    <tr>
                        <td>${counter.count + (page.number * page.size)}</td>
                        <td>
                            <div class="book-cover-placeholder">
                                <i class="fas fa-book"></i>
                            </div>
                        </td>
                        <td><a href="/books/${book.id}" class="book-title">${book.title}</a></td>
                        <td>${book.author}</td>
                        <td>${book.genre}</td>
                        <td>$${book.price}</td>
                        <td>
                            <div class="action-buttons">
                                <a href="/books/${book.id}" class="action-btn view-btn">View</a>

                                <c:if test="${sessionScope.user.role == 'ADMIN' || sessionScope.user.role == 'MANAGER'}">
                                    <a href="/books/edit/${book.id}" class="action-btn edit-btn">Edit</a>
                                    <form action="/books/delete/${book.id}" method="post" style="display: inline;">
                                        <button type="submit" class="action-btn delete-btn">Delete</button>
                                    </form>
                                </c:if>

                                <form action="/orders/add/${book.id}" method="post" style="display: inline;">
                                    <button type="submit" class="action-btn order-btn">Order</button>
                                </form>
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
                of ${page.totalElements} books
            </div>
        </div>
    </div>
</body>
</html>