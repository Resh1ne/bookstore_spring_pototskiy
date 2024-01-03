<!DOCTYPE html>
<html>
  <head>
    <title>Create User</title>
    <link href="css/style.css" rel="stylesheet" type="text/css" />
  </head>
  <body>
    <jsp:include page="../navbar.jsp" />
    <h1>Register new user</h1>
    <form method="post" action="controller">
      <input name="command" type="hidden" value="create_user" />
      <label>Email:<input name="email" type="email" required /></label>
      <br />
      <label>Password:<input name="password" type="password" required /></label>
      <br />
      <input type="submit" value="CREATE" />
    </form>
  </body>
</html>
