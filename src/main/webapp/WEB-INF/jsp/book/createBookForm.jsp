<!DOCTYPE html>
<html>
<head>
    <title>Add New Book | Book Haven</title>
    <link href="/css/style.css" rel="stylesheet" type="text/css" />
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
</head>
<body>
    <jsp:include page="../navbar.jsp" />

    <div class="form-container">
        <h1 class="form-header">Add New Book</h1>

        <form method="post" action="/books/create">
            <div class="form-group">
                <label for="title">Title</label>
                <input type="text" id="title" name="title" class="form-control" required>
            </div>

            <div class="form-group">
                <label for="isbn">ISBN</label>
                <input type="text" id="isbn" name="isbn" class="form-control" required>
            </div>

            <div class="form-group">
                <label for="author">Author</label>
                <input type="text" id="author" name="author" class="form-control">
            </div>

            <div class="form-group">
                <label for="pages">Pages</label>
                <input type="number" id="pages" name="pages" class="form-control" min="1">
            </div>

            <div class="form-group">
                <label for="publicationYear">Publication Year</label>
                <input type="number" id="publicationYear" name="publicationYear" class="form-control"
                       min="1000" max="${java.time.Year.now().value}">
            </div>

            <div class="form-group">
                <label for="price">Price ($)</label>
                <input type="number" id="price" name="price" class="form-control" min="0" step="0.01">
            </div>

            <div class="form-group">
                <label for="language">Language</label>
                <select id="language" name="language" class="form-control">
                    <option value="ENGLISH">English</option>
                    <option value="CHINESE">Chinese</option>
                    <option value="SPANISH">Spanish</option>
                    <option value="ARABIC">Arabic</option>
                    <option value="FRENCH">French</option>
                    <option value="RUSSIAN">Russian</option>
                    <option value="PORTUGUESE">Portuguese</option>
                    <option value="JAPANESE">Japanese</option>
                    <option value="GERMAN">German</option>
                    <option value="KOREAN">Korean</option>
                    <option value="TURKISH">Turkish</option>
                    <option value="ITALIAN">Italian</option>
                </select>
            </div>

            <div class="form-group">
                <label for="genre">Genre</label>
                <select id="genre" name="genre" class="form-control">
                    <option value="REALISM">Realism</option>
                    <option value="SCIENCE_FICTION">Science Fiction</option>
                    <option value="THRILLER">Thriller</option>
                    <option value="ROMANCE">Romance</option>
                    <option value="ADVENTURE">Adventure</option>
                    <option value="HORROR">Horror</option>
                    <option value="HISTORICAL_FICTION">Historical Fiction</option>
                    <option value="DRAMA">Drama</option>
                    <option value="COMEDY">Comedy</option>
                    <option value="FANTASY">Fantasy</option>
                    <option value="NON_FICTION">Non-Fiction</option>
                </select>
            </div>

            <div class="form-actions">
                <button type="submit" class="btn btn-primary">Add Book</button>
                <a href="/books" class="btn btn-secondary">Cancel</a>
            </div>
        </form>
    </div>
</body>
</html>