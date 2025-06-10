package com.belhard.bookstore.data.repository.impl;

import com.belhard.bookstore.data.entity.Book;
import com.belhard.bookstore.data.repository.BookRepository;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Repository
@Transactional
public class BookRepositoryImpl implements BookRepository {
    private static final String GET_ALL = "from Book";
    private static final String FIND_BY_AUTHOR = "SELECT b FROM Book b WHERE b.author = :author";
    private static final String FIND_BY_ISBN = "SELECT b FROM Book b WHERE b.isbn = :isbn";
    @PersistenceContext
    private EntityManager manager;

    @Override
    public Optional<Book> findByIsbn(String isbn) {
        return manager
                .createQuery(FIND_BY_ISBN, Book.class)
                .setParameter("isbn", isbn)
                .getResultStream()
                .findFirst();
    }

    @Override
    public List<Book> findByAuthor(String author) {
        return manager
                .createQuery(FIND_BY_AUTHOR, Book.class)
                .setParameter("author", author)
                .getResultList();
    }

    @Override
    public Optional<Book> findById(Long key) {
        return Optional.ofNullable(manager.find(Book.class, key));
    }

    @Override
    public List<Book> findAll() {
        return manager.createQuery(GET_ALL, Book.class).getResultList();
    }

    @Override
    public Book save(Book entity) {
        if (entity.getId() == null) {
            manager.persist(entity);
            return entity;
        } else {
            return manager.merge(entity);
        }
    }

    @Override
    public boolean delete(Long key) {
        Book book = manager.find(Book.class, key);
        if (book == null) {
            return false;
        }
        manager.remove(book);
        return true;
    }
}
