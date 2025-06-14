<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<!DOCTYPE html>
<html>
  <head>
    <title>Edit</title>
    <link href="/css/style.css" rel="stylesheet" type="text/css" />
  </head>
  <body>
    <jsp:include page="../navbar.jsp" />
    <h1>Register new user</h1>
    <form method="post" action="/users/edit/${user.id}">
      <input name="id" type="hidden" value="${user.id}" />
      <label
        >Email:<input name="email" type="email" required value="${user.email}"
      /></label>
      <br />
      <label
        >Password:<input
          name="password"
          type="password"
          required
          value="${user.password}"
      /></label>
      <br />
      <label
        >First name:<input
          name="firstName"
          type="text"
          value="${user.firstName}"
      /></label>
      <br />
      <label
        >Last name:<input name="lastName" type="text" value="${user.lastName}"
      /></label>
      <br />
      <label>Age:<input name="age" type="number" required value="${user.age}" /></label>
      <br />
      <label
        >Customer:<input name="role" type="radio" value="CUSTOMER"
        ${requestScope.user.role=='CUSTOMER' ? 'checked' : ''}></label
      >
      <label
        >Manager:<input name="role" type="radio" value="MANAGER"
        ${requestScope.user.role=='MANAGER' ? 'checked' : ''}></label
      >
      <label
        >Admin:<input name="role" type="radio" value="ADMIN"
        ${requestScope.user.role=='ADMIN' ? 'checked' : ''}></label
      >
      <br />
      <input type="submit" value="Update" />
    </form>
  </body>
</html>
