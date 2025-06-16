<!DOCTYPE html>
<html>
<head>
    <title>${user.firstName} ${user.lastName} | Book Haven</title>
    <link href="/css/style.css" rel="stylesheet" type="text/css" />
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
</head>
<body>
    <jsp:include page="../navbar.jsp" />

    <div class="user-profile">
        <div class="user-header">
            <h1>${user.firstName} ${user.lastName}</h1>
            <p class="text-muted">Last updated: ${date}</p>
        </div>

        <div class="user-details">
            <div class="user-detail-label">ID:</div>
            <div class="user-detail-value">${user.id}</div>

            <div class="user-detail-label">Email:</div>
            <div class="user-detail-value">${user.email}</div>

            <div class="user-detail-label">Role:</div>
            <div class="user-detail-value">${user.role}</div>

            <div class="user-detail-label">Age:</div>
            <div class="user-detail-value">${user.age}</div>
        </div>

        <div class="user-actions">
            <a href="/users" class="btn btn-primary">Back to All Users</a>
            <a href="/users/edit/${user.id}" class="btn btn-warning">Edit Profile</a>
        </div>
    </div>
</body>
</html>