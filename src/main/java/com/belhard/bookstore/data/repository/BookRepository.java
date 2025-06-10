package com.belhard.bookstore.data.repository;

import com.belhard.bookstore.data.entity.Book;

import java.util.List;
import java.util.Optional;

public interface BookRepository extends CrudRepository<Book, Long> {

    @SuppressWarnings("unused")
    Optional<Book> findByIsbn(String isbn);

    @SuppressWarnings("unused")
    List<Book> findByAuthor(String author);
}
