package com.belhard.bookstore.data.dao.impl;

import com.belhard.bookstore.data.connection.DataSource;
import com.belhard.bookstore.data.dao.BookDao;
import com.belhard.bookstore.data.entity.Book;
import com.belhard.bookstore.data.entity.enums.GenresOfTheBook;
import com.belhard.bookstore.data.entity.enums.LanguagesOfTheBook;
import lombok.RequiredArgsConstructor;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Types;
import java.util.ArrayList;
import java.util.List;

@RequiredArgsConstructor
public class BookDaoImpl implements BookDao {
    private final DataSource dataSource;
    private static final String CREATION_QUERY = "INSERT INTO books " +
            "(author, isbn, title, pages, publication_year, genre_id, language_id, price) " +
            "VALUES (?, ?, ?, ?, ?, (SELECT id FROM genres g WHERE g.genre = ?), (SELECT id FROM languages l WHERE l.language = ?), ?)";
    private static final String FIND_ALL_QUERY = "SELECT b.id, b.author, b.isbn, b.title, b.pages, b.publication_year, g.genre, l.language, b.price " +
            "FROM books b " +
            "JOIN genres g ON b.genre_id = g.id " +
            "JOIN languages l ON b.language_id = l.id ";
    private static final String FIND_BY_ID_QUERY = "SELECT b.id, b.author, b.isbn, b.title, b.pages, b.publication_year, g.genre, l.language, b.price " +
            "FROM books b " +
            "JOIN genres g ON b.genre_id = g.id " +
            "JOIN languages l ON b.language_id = l.id " +
            "WHERE b.id = ?";
    private static final String FIND_BY_ISBN = "SELECT b.id, b.author, b.isbn, b.title, b.pages, b.publication_year, g.genre, l.language, b.price " +
            "FROM books b " +
            "JOIN genres g ON b.genre_id = g.id " +
            "JOIN languages l ON b.language_id = l.id " +
            "WHERE b.isbn = ?";
    private static final String UPDATE_QUERY = "UPDATE books " +
            "SET " +
            "author = ?, " +
            "isbn = ?, " +
            "title = ?," +
            "pages = ?, " +
            "publication_year = ?, " +
            "genre_id = (SELECT id FROM genres g WHERE g.genre = ?), " +
            "language_id = (SELECT id FROM languages l WHERE l.language = ?), " +
            "price = ? " +
            "WHERE id = ?";
    private static final String DELETE_QUERY = "DELETE FROM books WHERE id = ?";
    private static final String FIND_BY_AUTHOR_QUERY = "SELECT b.id, b.author, b.isbn, b.title, b.pages, b.publication_year, g.genre, l.language, b.price " +
            "FROM books b " +
            "JOIN genres g ON b.genre_id = g.id " +
            "JOIN languages l ON b.language_id = l.id " +
            "WHERE b.author = ?";
    private static final String COUNT_QUERY = "SELECT COUNT(*) FROM books";
    private static final Logger log = LogManager.getLogger(BookDaoImpl.class);

    @Override
    public Book create(Book book) {
        try (Connection connection = dataSource.getConnection()) {
            log.debug("connecting to the database");
            PreparedStatement statement = connection.prepareStatement(CREATION_QUERY, Statement.RETURN_GENERATED_KEYS);
            statement.setString(1, book.getAuthor());
            statement.setString(2, book.getIsbn());
            statement.setString(3, book.getTitle());
            setNullInt(4, book.getPages(), statement);
            setNullInt(5, book.getPublicationYear(), statement);
            statement.setString(6, book.getGenre().toString());
            statement.setString(7, book.getLanguage().toString());
            setNullBigDecimal(8, book.getPrice(), statement);
            statement.executeUpdate();
            ResultSet keys = statement.getGeneratedKeys();
            if (keys.next()) {
                long id = keys.getLong("id");
                return findById(id);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        throw new RuntimeException("Can't create book: " + book);
    }

    private void setNullInt(int index, Integer value, PreparedStatement statement) throws SQLException {
        if (value == null) {
            statement.setNull(index, Types.INTEGER);
        } else {
            statement.setInt(index, value);
        }
    }
private void setNullBigDecimal(int index, BigDecimal value, PreparedStatement statement) throws SQLException {
        if (value == null) {
            statement.setNull(index, Types.DOUBLE);
        } else {
            statement.setBigDecimal(index, value);
        }
    }

    @Override
    public List<Book> findAll() {
        List<Book> books = new ArrayList<>();
        try (Connection connection = dataSource.getConnection()) {
            log.debug("connecting to the database");
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery(FIND_ALL_QUERY);
            while (resultSet.next()) {
                Book book = mapRow(resultSet);
                books.add(book);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return books;
    }

    @Override
    public Book findById(Long id) {
        try (Connection connection = dataSource.getConnection()) {
            log.debug("connecting to the database");
            PreparedStatement statement = connection.prepareStatement(FIND_BY_ID_QUERY);
            statement.setLong(1, id);
            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next()) {
                return mapRow(resultSet);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return null;
    }

    @Override
    public Book findByIsbn(String isbn) {
        try (Connection connection = dataSource.getConnection()) {
            log.debug("connecting to the database");
            PreparedStatement statement = connection.prepareStatement(FIND_BY_ISBN);
            statement.setString(1, isbn);
            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next()) {
                return mapRow(resultSet);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return null;
    }

    @Override
    public List<Book> findByAuthor(String author) {
        List<Book> books = new ArrayList<>();
        try (Connection connection = dataSource.getConnection()) {
            log.debug("connecting to the database");
            PreparedStatement statement = connection.prepareStatement(FIND_BY_AUTHOR_QUERY);
            statement.setString(1, author);
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
                Book book = mapRow(resultSet);
                books.add(book);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return books;
    }

    private static Book mapRow(ResultSet resultSet) throws SQLException {
        Book book = new Book();
        book.setId(resultSet.getLong("id"));
        book.setAuthor(resultSet.getString("author"));
        book.setIsbn(resultSet.getString("isbn"));
        book.setTitle(resultSet.getString("title"));
        book.setPages(resultSet.getInt("pages"));
        book.setPublicationYear(resultSet.getInt("publication_year"));
        book.setGenre(GenresOfTheBook.valueOf(resultSet.getString("genre")));
        book.setLanguage(LanguagesOfTheBook.valueOf(resultSet.getString("language")));
        book.setPrice(resultSet.getBigDecimal("price"));
        return book;
    }

    @Override
    public Book update(Book book) {
        try (Connection connection = dataSource.getConnection()) {
            log.debug("connecting to the database");
            PreparedStatement statement = connection.prepareStatement(UPDATE_QUERY);
            statement.setString(1, book.getAuthor());
            statement.setString(2, book.getIsbn());
            statement.setString(3, book.getTitle());
            setNullInt(4, book.getPages(), statement);
            statement.setInt(5, book.getPublicationYear());
            statement.setString(6, book.getGenre().toString());
            statement.setString(7, book.getLanguage().toString());
            setNullBigDecimal(8, book.getPrice(), statement);
            statement.setLong(9, book.getId());

            int rowsAffected = statement.executeUpdate();

            if (rowsAffected > 0) {
                return findById(book.getId());
            } else {
                throw new RuntimeException("Failed to update book. No rows affected.");
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public boolean delete(Long id) {
        try (Connection connection = dataSource.getConnection()) {
            log.debug("connecting to the database");
            PreparedStatement statement = connection.prepareStatement(DELETE_QUERY);
            statement.setLong(1, id);
            int rowsAffected = statement.executeUpdate();

            return rowsAffected > 0;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public long countAll() {
        try (Connection connection = dataSource.getConnection()) {
            log.debug("connecting to the database");
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery(COUNT_QUERY);
            if (resultSet.next()) {
                return resultSet.getLong("count");
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        throw new RuntimeException("The values could not be calculated");
    }
}
