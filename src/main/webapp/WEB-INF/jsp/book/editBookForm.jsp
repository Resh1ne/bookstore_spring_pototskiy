<!DOCTYPE html>
<html>
<head>
    <title>Edit ${book.title} | Book Haven</title>
    <link href="/css/style.css" rel="stylesheet" type="text/css" />
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
</head>
<body>
    <jsp:include page="../navbar.jsp" />

    <div class="form-container">
        <h1 class="form-header">Edit Book: ${book.title}</h1>

        <form method="post" action="/books/edit/${book.id}">
            <input type="hidden" name="id" value="${book.id}">

            <div class="form-group">
                <label for="title">Title</label>
                <input type="text" id="title" name="title" class="form-control"
                       value="${book.title}" required>
            </div>

            <div class="form-group">
                <label for="isbn">ISBN</label>
                <input type="text" id="isbn" name="isbn" class="form-control"
                       value="${book.isbn}" required>
            </div>

            <div class="form-group">
                <label for="author">Author</label>
                <input type="text" id="author" name="author" class="form-control"
                       value="${book.author}">
            </div>

            <div class="form-group">
                <label for="pages">Pages</label>
                <input type="number" id="pages" name="pages" class="form-control"
                       value="${book.pages}" min="1">
            </div>

            <div class="form-group">
                <label for="publicationYear">Publication Year</label>
                <input type="number" id="publicationYear" name="publicationYear"
                       class="form-control" value="${book.publicationYear}"
                       min="1000" max="${java.time.Year.now().value}">
            </div>

            <div class="form-group">
                <label for="price">Price ($)</label>
                <input type="number" id="price" name="price" class="form-control"
                       value="${book.price}" min="0" step="0.01">
            </div>

            <div class="form-group">
                <label for="language">Language</label>
                <select id="language" name="language" class="form-control">
                    <option value="ENGLISH" ${book.language=='ENGLISH' ? 'selected' : ''}>English</option>
                    <option value="CHINESE" ${book.language=='CHINESE' ? 'selected' : ''}>Chinese</option>
                    <option value="SPANISH" ${book.language=='SPANISH' ? 'selected' : ''}>Spanish</option>
                    <option value="ARABIC" ${book.language=='ARABIC' ? 'selected' : ''}>Arabic</option>
                    <option value="FRENCH" ${book.language=='FRENCH' ? 'selected' : ''}>French</option>
                    <option value="RUSSIAN" ${book.language=='RUSSIAN' ? 'selected' : ''}>Russian</option>
                    <option value="PORTUGUESE" ${book.language=='PORTUGUESE' ? 'selected' : ''}>Portuguese</option>
                    <option value="JAPANESE" ${book.language=='JAPANESE' ? 'selected' : ''}>Japanese</option>
                    <option value="GERMAN" ${book.language=='GERMAN' ? 'selected' : ''}>German</option>
                    <option value="KOREAN" ${book.language=='KOREAN' ? 'selected' : ''}>Korean</option>
                    <option value="TURKISH" ${book.language=='TURKISH' ? 'selected' : ''}>Turkish</option>
                    <option value="ITALIAN" ${book.language=='ITALIAN' ? 'selected' : ''}>Italian</option>
                </select>
            </div>

            <div class="form-group">
                <label for="genre">Genre</label>
                <select id="genre" name="genre" class="form-control">
                    <option value="REALISM" ${book.genre=='REALISM' ? 'selected' : ''}>Realism</option>
                    <option value="SCIENCE_FICTION" ${book.genre=='SCIENCE_FICTION' ? 'selected' : ''}>Science Fiction</option>
                    <option value="THRILLER" ${book.genre=='THRILLER' ? 'selected' : ''}>Thriller</option>
                    <option value="ROMANCE" ${book.genre=='ROMANCE' ? 'selected' : ''}>Romance</option>
                    <option value="ADVENTURE" ${book.genre=='ADVENTURE' ? 'selected' : ''}>Adventure</option>
                    <option value="HORROR" ${book.genre=='HORROR' ? 'selected' : ''}>Horror</option>
                    <option value="HISTORICAL_FICTION" ${book.genre=='HISTORICAL_FICTION' ? 'selected' : ''}>Historical Fiction</option>
                    <option value="DRAMA" ${book.genre=='DRAMA' ? 'selected' : ''}>Drama</option>
                    <option value="COMEDY" ${book.genre=='COMEDY' ? 'selected' : ''}>Comedy</option>
                    <option value="FANTASY" ${book.genre=='FANTASY' ? 'selected' : ''}>Fantasy</option>
                    <option value="NON_FICTION" ${book.genre=='NON_FICTION' ? 'selected' : ''}>Non-Fiction</option>
                </select>
            </div>

            <div class="form-actions">
                <button type="submit" class="btn btn-primary">Update Book</button>
                <a href="/books/${book.id}" class="btn btn-secondary">Cancel</a>
            </div>
        </form>
    </div>
</body>
</html>