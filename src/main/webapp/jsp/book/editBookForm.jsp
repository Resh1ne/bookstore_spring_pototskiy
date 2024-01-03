<!DOCTYPE html>
<html>
  <head>
    <title>Edit</title>
    <link href="css/style.css" rel="stylesheet" type="text/css" />
  </head>
  <body>
    <jsp:include page="../navbar.jsp" />
    <h1>Register new book</h1>
    <form method="post" action="controller">
      <input name="command" type="hidden" value="edit_book" />
      <input name="id" type="hidden" value="${book.id}" />
      <label
        >Title:<input name="title" type="text" required value="${book.title}"
      /></label>
      <br />
      <label
        >Isbn:<input name="isbn" type="text" required value="${book.isbn}"
      /></label>
      <br />
      <label
        >Author:<input name="author" type="text" value="${book.author}"
      /></label>
      <br />
      <label
        >Pages:<input name="pages" type="number" required value="${book.pages}"
      /></label>
      <br />
      <label
        >Publication year:<input name="publicationYear" required type="number"  value="${book.publicationYear}"
      /></label>
      <br />
      <label
        >Price:<input name="price" type="number" required value="${book.price}"
      /></label>
      <br />
      <label for="language">Enter language:</label>
      <select id="language" name="language">
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
      <br />
      <label for="genre">Select the book genre:</label>
      <select id="genre" name="genre">
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
      <br />
      <input type="submit" value="Update" />
    </form>
  </body>
</html>
