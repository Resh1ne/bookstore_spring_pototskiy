<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<html>
  <head>
    <title>Error</title>
    <link href="/css/style.css" rel="stylesheet" type="text/css" />
  </head>
  <body>
    <jsp:include page="../navbar.jsp" />
    <div class="error-container">
        <h1>${statusCode}</h1>
        <h1>Error</h1>
        <p>${errorMessage}</p>
    </div>
  </body>
</html>
