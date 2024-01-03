<%@ page language="java" contentType="text/html; charset=UTF-8"
pageEncoding="UTF-8" %> <%@ taglib prefix="c"
uri="http://java.sun.com/jsp/jstl/core" %>
<html>
  <head>
    <title>Books</title>
    <link href="css/style.css" rel="stylesheet" type="text/css" />
  </head>
  <body>
    <jsp:include page="../navbar.jsp" />
    <h1>All Books</h1>
    <table>
      <tr>
        <th>#</th>
        <th>Id</th>
        <th>Title</th>
      </tr>

      <c:forEach items="${books}" var="book" varStatus="counter">
        <tr>
          <td>${counter.count}</td>
          <td>${book.id}</td>
          <td>
            <a href="controller?command=book&id=${book.id}">${book.title}</a>
          </td>
          <td>
            <a href="controller?command=edit_book_form&id=${book.id}">Edit</a>
          </td>
        </tr>
      </c:forEach>
    </table>
  </body>
</html>
