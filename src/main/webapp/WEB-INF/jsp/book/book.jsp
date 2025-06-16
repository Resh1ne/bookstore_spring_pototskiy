<!DOCTYPE html>
<html>
<head>
    <title>${book.title} | Book Haven</title>
    <link href="/css/style.css" rel="stylesheet" type="text/css" />
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
</head>
<body>
    <jsp:include page="../navbar.jsp" />

    <div class="book-container">
        <div class="book-header">
            <h1>${book.title}</h1>
            <p class="text-muted">Last updated: ${date}</p>
        </div>

        <div class="book-details">
            <div class="book-detail-label">ID:</div>
            <div class="book-detail-value">${book.id}</div>

            <div class="book-detail-label">ISBN:</div>
            <div class="book-detail-value">${book.isbn}</div>

            <div class="book-detail-label">Author:</div>
            <div class="book-detail-value">${book.author}</div>

            <div class="book-detail-label">Pages:</div>
            <div class="book-detail-value">${book.pages}</div>

            <div class="book-detail-label">Publication Year:</div>
            <div class="book-detail-value">${book.publicationYear}</div>

            <div class="book-detail-label">Genre:</div>
            <div class="book-detail-value">${book.genre}</div>

            <div class="book-detail-label">Language:</div>
            <div class="book-detail-value">${book.language}</div>

            <div class="book-detail-label">Price:</div>
            <div class="book-detail-value">$${book.price}</div>
        </div>

        <div class="book-actions">
            <a href="/books" class="btn btn-primary">Back to All Books</a>

            <c:if test="${sessionScope.user.role == 'ADMIN' || sessionScope.user.role == 'MANAGER'}">
                <a href="/books/edit/${book.id}" class="btn btn-warning">Edit Book</a>
            </c:if>

            <form action="/orders/add/${book.id}" method="post" style="display: inline;">
                <button type="submit" class="btn order-btn">Add to Order</button>
            </form>
        </div>
    </div>
</body>
</html>