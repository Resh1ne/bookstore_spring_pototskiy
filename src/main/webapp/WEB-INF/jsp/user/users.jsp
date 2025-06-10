<%@ page language="java" contentType="text/html; charset=UTF-8"
pageEncoding="UTF-8" %> <%@ taglib prefix="c"
uri="http://java.sun.com/jsp/jstl/core" %>
<html>
  <head>
    <title>Users</title>
    <link href="css/style.css" rel="stylesheet" type="text/css" />
  </head>
  <body>
    <jsp:include page="../navbar.jsp" />
    <h1>All Users</h1>
    <table>
      <tr>
        <th>#</th>
        <th>Id</th>
        <th>Email</th>
      </tr>

      <c:forEach items="${users}" var="user" varStatus="counter">
        <tr>
          <td>${counter.count}</td>
          <td>${user.id}</td>
          <td>
            <a href="controller?command=user&id=${user.id}">${user.email}</a>
          </td>
          <td>
            <a href="controller?command=edit_user_form&id=${user.id}">Edit</a>
          </td>
        </tr>
      </c:forEach>
    </table>
  </body>
</html>
