<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
    <!DOCTYPE html>
    <html lang="en">

    <head>
        <link rel="stylesheet" type="text/css" href="/css/style.css">
        <meta charset="UTF-8">
        <meta name="viewport" content="width=device-width, initial-scale=1.0">
        <title>Home</title>
        <jsp:include page="../navbar.jsp" />
    </head>
    <h1>Sign in</h1>

    <body>
        <form action="/login" method="post">

            <label for="email">Email:</label>
            <input type="email" id="email" name="email" required><br>

            <label for="password">Password:</label>
            <input type="password" id="password" name="password" required><br>

            <br />
            <button type="submit">Sign in</button>
        </form>
        <br />
        <a href="/users/create">Go to sign up</a>
    </body>