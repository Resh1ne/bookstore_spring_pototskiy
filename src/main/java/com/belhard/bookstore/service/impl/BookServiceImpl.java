package com.belhard.bookstore.service.impl;

import com.belhard.bookstore.data.entity.Book;
import com.belhard.bookstore.data.repository.BookRepository;
import com.belhard.bookstore.service.BookService;
import com.belhard.bookstore.service.dto.BookDto;
import com.belhard.bookstore.service.exception.ResourceNotFoundException;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@RequiredArgsConstructor
@Service
@Log4j2
public class BookServiceImpl implements BookService {
    private final BookRepository bookRepository;
    private final ModelMapper mapper;

    @Override
    public BookDto create(BookDto dto) {
        Book book = mapper.map(dto, Book.class);
        Book bookCreated = bookRepository.save(validateForCreate(book));
        log.info("Created new book with id: {}", bookCreated.getId());
        return mapper.map(bookCreated, BookDto.class);
    }

    private Book validateForCreate(Book book) {
        book.setAuthor(null);
        book.setPages(null);
        book.setPrice(null);
        book.setPublicationYear(null);
        return book;
    }

    @Override
    @Transactional
    public Page<BookDto> getAll(Pageable page) {
        return bookRepository
                .findAll(page)
                .map(e -> mapper.map(e, BookDto.class));
    }

    @Override
    @Transactional
    public BookDto getById(long id) {
        return bookRepository
                .findById(id)
                .map(e -> mapper.map(e, BookDto.class))
                .orElseThrow(() -> new ResourceNotFoundException("Book not found with id: " + id));
    }

    @Override
    public BookDto update(BookDto dto) {
        Book book = mapper.map(dto, Book.class);
        Book bookCreated = bookRepository.save(book);
        log.info("Updated book with id: {}", bookCreated.getId());
        return mapper.map(bookCreated, BookDto.class);
    }

    @Override
    public void delete(long id) {
        if (!bookRepository.existsById(id)) {
            throw new ResourceNotFoundException("Book with id: " + id + " not found");
        }
        bookRepository.deleteById(id);
        log.info("Deleted book with id: {}", id);
    }
}
