<!DOCTYPE html>
<html>
<head>
    <title>Edit User | Book Haven</title>
    <link href="/css/style.css" rel="stylesheet" type="text/css" />
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
</head>
<body>
    <jsp:include page="../navbar.jsp" />

    <div class="form-container">
        <h1 class="form-header">Edit User: ${user.email}</h1>

        <form method="post" action="/users/edit/${user.id}">
            <input type="hidden" name="id" value="${user.id}">

            <div class="form-group">
                <label for="email">Email</label>
                <input type="email" id="email" name="email" class="form-control"
                       value="${user.email}" required>
            </div>

            <div class="form-group">
                <label for="password">Password</label>
                <input type="password" id="password" name="password" class="form-control"
                       placeholder="Enter new password to change">
            </div>

            <div class="form-group">
                <label for="firstName">First Name</label>
                <input type="text" id="firstName" name="firstName" class="form-control"
                       value="${user.firstName}">
            </div>

            <div class="form-group">
                <label for="lastName">Last Name</label>
                <input type="text" id="lastName" name="lastName" class="form-control"
                       value="${user.lastName}">
            </div>

            <div class="form-group">
                <label for="age">Age</label>
                <input type="number" id="age" name="age" class="form-control"
                       value="${user.age}" min="13" max="120">
            </div>

            <div class="form-group">
                <label>Role</label>
                <div class="radio-group">
                    <div class="radio-option">
                        <input type="radio" id="customer" name="role" value="CUSTOMER"
                               ${user.role=='CUSTOMER' ? 'checked' : ''}>
                        <label for="customer">Customer</label>
                    </div>
                    <div class="radio-option">
                        <input type="radio" id="manager" name="role" value="MANAGER"
                               ${user.role=='MANAGER' ? 'checked' : ''}>
                        <label for="manager">Manager</label>
                    </div>
                    <div class="radio-option">
                        <input type="radio" id="admin" name="role" value="ADMIN"
                               ${user.role=='ADMIN' ? 'checked' : ''}>
                        <label for="admin">Admin</label>
                    </div>
                </div>
            </div>

            <div class="form-actions">
                <button type="submit" class="btn btn-primary">Update User</button>
                <a href="/users/${user.id}" class="btn btn-secondary">Cancel</a>
            </div>
        </form>
    </div>
</body>
</html>