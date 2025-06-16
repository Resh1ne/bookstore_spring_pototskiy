<!DOCTYPE html>
<html>
<head>
    <title>Error | Book Haven</title>
    <link href="/css/style.css" rel="stylesheet" type="text/css" />
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
</head>
<body>
    <jsp:include page="../navbar.jsp" />
    <div class="error-container">
        <h1>${statusCode}</h1>
        <h1>Oops! Something went wrong.</h1>
        <p>${errorMessage}</p>
        <a href="/" class="navbar_style" style="background-color: var(--accent-color);">Return Home</a>
    </div>
</body>
</html>