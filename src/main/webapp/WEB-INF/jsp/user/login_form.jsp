<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<!DOCTYPE html>
<html>
<head>
    <title>Login | Book Haven</title>
    <link href="/css/style.css" rel="stylesheet" type="text/css" />
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
</head>
<body>
    <jsp:include page="../navbar.jsp" />

    <div class="login-container">
        <h1 class="login-header">Sign In</h1>

        <form method="post" action="/login">
            <div class="form-group">
                <label for="email">Email</label>
                <input type="email" id="email" name="email" class="form-control" required>
            </div>

            <div class="form-group">
                <label for="password">Password</label>
                <input type="password" id="password" name="password" class="form-control" required>
            </div>

            <div class="form-actions">
                <button type="submit" class="btn btn-primary">Sign In</button>
            </div>

            <div class="login-actions">
                <p>Do not have an account? <a href="/users/create" class="login-link">Sign up</a></p>
            </div>
        </form>
    </div>
</body>
</html>