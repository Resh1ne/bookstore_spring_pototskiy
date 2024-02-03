package com.belhard.bookstore.data.repository;

import com.belhard.bookstore.data.entity.Book;

import java.util.List;

public interface BookRepository {
    Book create(Book book);

    Book findById(Long id);

    List<Book> findAll();

    Book update(Book book);

    boolean delete(Long id);

    @SuppressWarnings("unused")
    Book findByIsbn(String isbn);

    @SuppressWarnings("unused")
    List<Book> findByAuthor(String author);

    long countAll();
}
