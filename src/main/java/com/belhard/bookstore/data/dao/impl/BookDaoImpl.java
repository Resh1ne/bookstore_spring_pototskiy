package com.belhard.bookstore.data.dao.impl;

import com.belhard.bookstore.data.dao.BookDao;
import com.belhard.bookstore.data.dto.BookDto;
import com.belhard.bookstore.data.entity.Book;
import com.belhard.bookstore.data.entity.enums.GenresOfTheBook;
import lombok.RequiredArgsConstructor;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Types;
import java.util.List;
import java.util.Map;

@RequiredArgsConstructor
@Repository
public class BookDaoImpl implements BookDao {
    private final JdbcTemplate jdbcTemplate;
    private final NamedParameterJdbcTemplate namedParameterJdbcTemplate;
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
            "author = :author, " +
            "isbn = :isbn, " +
            "title = :title," +
            "pages = :pages, " +
            "publication_year = :publication_year, " +
            "genre_id = (SELECT id FROM genres g WHERE g.genre = :genre), " +
            "language_id = (SELECT id FROM languages l WHERE l.language = :language), " +
            "price = :price " +
            "WHERE id = :id";
    private static final String DELETE_QUERY = "DELETE FROM books WHERE id = ?";
    private static final String FIND_BY_AUTHOR_QUERY = "SELECT b.id, b.author, b.isbn, b.title, b.pages, b.publication_year, g.genre, l.language, b.price " +
            "FROM books b " +
            "JOIN genres g ON b.genre_id = g.id " +
            "JOIN languages l ON b.language_id = l.id " +
            "WHERE b.author = ?";
    private static final String COUNT_QUERY = "SELECT COUNT(*) FROM books";

    @Override
    public BookDto create(BookDto book) {
        KeyHolder keyHolder = new GeneratedKeyHolder();
        jdbcTemplate.update(connection -> {
            PreparedStatement statement = connection.prepareStatement(CREATION_QUERY, Statement.RETURN_GENERATED_KEYS);
            statement.setString(1, book.getAuthor());
            statement.setString(2, book.getIsbn());
            statement.setString(3, book.getTitle());
            setNullInt(4, book.getPages(), statement);
            setNullInt(5, book.getPublicationYear(), statement);
            statement.setString(6, book.getGenre().toString());
            statement.setString(7, book.getLanguage().toString());
            setNullBigDecimal(8, book.getPrice(), statement);
            return statement;
        }, keyHolder);
        List<Map<String, Object>> keys = keyHolder.getKeyList();
        Long id = null;
        for (Map<String, Object> key : keys) {
            id = (Long) key.get("id");
        }
        if (id == null) {
            throw new RuntimeException("Failed to create user");
        }
        return findById(id);
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
    public List<BookDto> findAll() {
        return jdbcTemplate.query(FIND_ALL_QUERY, this::mapRow);
    }

    @Override
    public BookDto findById(Long id) {
        return jdbcTemplate.queryForObject(FIND_BY_ID_QUERY, this::mapRow, id);
    }

    @Override
    public BookDto findByIsbn(String isbn) {
        return jdbcTemplate.queryForObject(FIND_BY_ISBN, this::mapRow, isbn);
    }

    @Override
    public List<BookDto> findByAuthor(String author) {
        return jdbcTemplate.query(FIND_BY_AUTHOR_QUERY, this::mapRow, author);
    }

    private BookDto mapRow(ResultSet resultSet, int rowNum) throws SQLException {
        BookDto book = new BookDto();
        book.setId(resultSet.getLong("id"));
        book.setAuthor(resultSet.getString("author"));
        book.setIsbn(resultSet.getString("isbn"));
        book.setTitle(resultSet.getString("title"));
        book.setPages(resultSet.getInt("pages"));
        book.setPublicationYear(resultSet.getInt("publication_year"));
        book.setGenre(GenresOfTheBook.valueOf(resultSet.getString("genre")));
        book.setLanguage(Book.Language.valueOf(resultSet.getString("language")));
        book.setPrice(resultSet.getBigDecimal("price"));
        return book;
    }

    @Override
    public BookDto update(BookDto book) {
        SqlParameterSource params = new MapSqlParameterSource()
                .addValue("author", book.getAuthor())
                .addValue("isbn", book.getIsbn())
                .addValue("title", book.getTitle())
                .addValue("pages", book.getPages())
                .addValue("publication_year", book.getPublicationYear())
                .addValue("genre", book.getGenre().toString())
                .addValue("language", book.getLanguage().toString())
                .addValue("price", book.getPrice())
                .addValue("id", book.getId());
        int rowsUpdated = namedParameterJdbcTemplate.update(UPDATE_QUERY, params);
        if (rowsUpdated == 0) {
            throw new RuntimeException("Can't update book with id: " + book.getId());
        }
        return findById(book.getId());
    }

    @Override
    public boolean delete(Long id) {
        int rowsUpdated = jdbcTemplate.update(DELETE_QUERY, id);
        return rowsUpdated == 1;
    }

    @Override
    public long countAll() {
        Long count = jdbcTemplate.queryForObject(COUNT_QUERY, long.class);
        if (count == null) {
            return 0;
        }
        return count;
    }
}
