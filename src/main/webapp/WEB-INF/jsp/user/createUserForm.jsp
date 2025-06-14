<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<html>
  <head>
    <title>Create User</title>
    <link href="/css/style.css" rel="stylesheet" type="text/css" />
    <link href="/css/login.css" rel="stylesheet" type="text/css" />
  </head>
  <body>
    <jsp:include page="../navbar.jsp" />
    <h1>Register new user</h1>
    <form:form class="login-form" action="/users/create" method="post" modelAttribute="userDto">
        <form:errors cssClass="error-message" path="email"/>
        <label>Login: <form:input path="email" type="text"/></label>
        <form:errors cssClass="error-message" path="password"/>
        <label>Password: <form:input path="password" type="text"/></label>
        <button>Save</button>
    </form:form>
  </body>
</html>
