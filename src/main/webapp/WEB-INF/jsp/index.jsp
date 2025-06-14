<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<html>
  <head>
    <title>Home</title>
    <link href="/css/style.css" rel="stylesheet" type="text/css" />
  </head>
  <body>
    <jsp:include page="navbar.jsp" />
    <img src="images/img.png" alt="bookstore" />
    <h1>Welcome!</h1>
    <p><spring:message code="home.greet"/></p>
  </body>
</html>
