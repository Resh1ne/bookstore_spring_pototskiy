package com.belhard.bookstore.service.impl;

import com.belhard.bookstore.data.entity.Book;
import com.belhard.bookstore.data.repository.BookRepository;
import com.belhard.bookstore.service.BookService;
import com.belhard.bookstore.service.dto.BookDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@RequiredArgsConstructor
@Service
@Log4j2
public class BookServiceImpl implements BookService {
    private final BookRepository bookRepository;

    @Override
    public BookDto create(BookDto dto) {
        Book book = toBookEntity(dto);
        Book bookCreated = bookRepository.save(validateForCreate(book));
        log.info("Created new book with id: {}", bookCreated.getId());
        return toBookDto(bookCreated);
    }

    private Book validateForCreate(Book book) {
        book.setAuthor(null);
        book.setPages(null);
        book.setPrice(null);
        book.setPublicationYear(null);
        return book;
    }

    private Book toBookEntity(BookDto dto) {
        Book bookEntity = new Book();
        bookEntity.setAuthor(dto.getAuthor());
        bookEntity.setGenre(dto.getGenre());
        bookEntity.setIsbn(dto.getIsbn());
        bookEntity.setLanguage(dto.getLanguage());
        bookEntity.setPages(dto.getPages());
        bookEntity.setPrice(dto.getPrice());
        bookEntity.setPublicationYear(dto.getPublicationYear());
        bookEntity.setTitle(dto.getTitle());
        return bookEntity;
    }

    @Override
    @Transactional
    public List<BookDto> getAll() {
        return bookRepository
                .findAll()
                .stream()
                .map(this::toBookDto)
                .toList();
    }

    @Override
    @Transactional
    public BookDto getById(long id) {
        return bookRepository
                .findById(id)
                .map(this::toBookDto)
                .orElseThrow(() -> new RuntimeException("Book not found with id: " + id));
    }

    @Override
    public BookDto update(BookDto dto) {
        Book book = toBookEntity(dto);
        book.setId(dto.getId());
        Book bookCreated = bookRepository.save(book);
        log.info("Updated book with id: {}", bookCreated.getId());
        return toBookDto(bookCreated);
    }

    @Override
    public void delete(long id) {
        if (!bookRepository.delete(id)) {
            throw new RuntimeException("Book with id: " + id + " not found");
        }
        log.info("Deleted book with id: {}", id);
    }

    private BookDto toBookDto(Book book) {
        BookDto bookDto = new BookDto();
        bookDto.setAuthor(book.getAuthor());
        bookDto.setId(book.getId());
        bookDto.setGenre(book.getGenre());
        bookDto.setIsbn(book.getIsbn());
        bookDto.setPages(book.getPages());
        bookDto.setLanguage(book.getLanguage());
        bookDto.setPrice(book.getPrice());
        bookDto.setPublicationYear(book.getPublicationYear());
        bookDto.setTitle(book.getTitle());
        return bookDto;
    }
}
