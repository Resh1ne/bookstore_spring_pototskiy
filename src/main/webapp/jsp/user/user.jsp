<html>
  <head>
    <title>User</title>
    <link href="css/style.css" rel="stylesheet" type="text/css" />
  </head>
  <body>
    <jsp:include page="../navbar.jsp" />
    <h1>User</h1>
    <h3>${date}</h3>
    <p>Id: ${user.id}</p>
    <p>Email: ${user.email}</p>
    <p>Password: ${user.password}</p>
  </body>
</html>
