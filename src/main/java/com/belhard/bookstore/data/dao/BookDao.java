package com.belhard.bookstore.data.dao;

import com.belhard.bookstore.data.dto.BookDto;

import java.util.List;

public interface BookDao {
    BookDto create(BookDto book);

    BookDto findById(Long id);

    List<BookDto> findAll();

    BookDto update(BookDto book);

    boolean delete(Long id);

    @SuppressWarnings("unused")
    BookDto findByIsbn(String isbn);

    @SuppressWarnings("unused")
    List<BookDto> findByAuthor(String author);

    long countAll();
}
