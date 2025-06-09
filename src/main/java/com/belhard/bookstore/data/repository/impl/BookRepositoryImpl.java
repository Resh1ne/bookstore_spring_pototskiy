package com.belhard.bookstore.data.repository.impl;

import com.belhard.bookstore.data.dao.BookDao;
import com.belhard.bookstore.data.dto.BookDto;
import com.belhard.bookstore.data.entity.Book;
import com.belhard.bookstore.data.repository.BookRepository;
import com.belhard.bookstore.data.util.DataMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@RequiredArgsConstructor
public class BookRepositoryImpl implements BookRepository {
    private final DataMapper dataMapper;
    private final BookDao bookDao;

    @Override
    public Book create(Book book) {
        BookDto bookDto = dataMapper.toDto(book);
        BookDto created = bookDao.create(bookDto);
        return dataMapper.toEntity(created);
    }

    @Override
    public Book findById(Long id) {
        return dataMapper.toEntity(bookDao.findById(id));
    }

    @Override
    public List<Book> findAll() {
        return dataMapper.toBooksEntity(bookDao.findAll());
    }

    @Override
    public Book update(Book book) {
        BookDto bookDto = dataMapper.toDto(book);
        BookDto updated = bookDao.update(bookDto);
        return dataMapper.toEntity(updated);
    }

    @Override
    public boolean delete(Long id) {
        return bookDao.delete(id);
    }

    @Override
    public Book findByIsbn(String isbn) {
        return dataMapper.toEntity(bookDao.findByIsbn(isbn));
    }

    @Override
    public List<Book> findByAuthor(String author) {
        return dataMapper.toBooksEntity(bookDao.findByAuthor(author));
    }

    @Override
    public long countAll() {
        return bookDao.countAll();
    }
}
