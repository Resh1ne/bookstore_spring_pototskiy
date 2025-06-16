<!DOCTYPE html>
<html>
<head>
    <title>Register New User | Book Haven</title>
    <link href="/css/style.css" rel="stylesheet" type="text/css" />
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
</head>
<body>
    <jsp:include page="../navbar.jsp" />

    <div class="form-container">
        <h1 class="form-header">Register New User</h1>

        <form method="post" action="/users/create">
            <div class="form-group">
                <label for="email">Email</label>
                <input type="email" id="email" name="email" class="form-control" required>
            </div>

            <div class="form-group">
                <label for="password">Password</label>
                <input type="password" id="password" name="password" class="form-control" required>
            </div>

            <div class="form-group">
                <label for="firstName">First Name</label>
                <input type="text" id="firstName" name="firstName" class="form-control">
            </div>

            <div class="form-group">
                <label for="lastName">Last Name</label>
                <input type="text" id="lastName" name="lastName" class="form-control">
            </div>

            <div class="form-group">
                <label for="age">Age</label>
                <input type="number" id="age" name="age" class="form-control" min="13" max="120">
            </div>

            <div class="form-actions">
                <button type="submit" class="btn btn-primary">Register</button>
                <a href="/login" class="btn btn-secondary">Cancel</a>
            </div>
        </form>
    </div>
</body>
</html>