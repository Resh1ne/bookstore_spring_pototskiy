<!DOCTYPE html>
<html>
  <head>
    <title>Create User</title>
    <link href="css/style.css" rel="stylesheet" type="text/css" />
  </head>
  <body>
    <jsp:include page="../navbar.jsp" />
    <h1>Register new book</h1>
    <form method="post" action="controller">
      <input name="command" type="hidden" value="create_book" />
      <label>Title:<input name="title" type="text" required /></label>
      <br />
      <label>Isbn:<input name="isbn" type="text" required /></label>
      <br />
      <label for="languages">Enter language:</label>
      <select id="languages" name="language">
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
      <br />
      <label for="genres">Select the book genre:</label>
      <select id="genres" name="genre">
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
      <br />
      <input type="submit" value="Create" />
    </form>
  </body>
</html>
