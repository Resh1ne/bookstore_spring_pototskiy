<html>
  <head>
    <title>Book</title>
    <link href="css/style.css" rel="stylesheet" type="text/css" />
  </head>
  <body>
    <jsp:include page="../navbar.jsp" />
    <h1>Book</h1>
    <h3>${date}</h3>
    <p>Id: ${book.id}</p>
    <p>Title: ${book.title}</p>
    <p>Isbn: ${book.isbn}</p>
  </body>
</html>
