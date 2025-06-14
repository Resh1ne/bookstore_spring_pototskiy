package com.belhard.bookstore.service;

import com.belhard.bookstore.service.dto.BookDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface BookService {
    BookDto create(BookDto dto);

    Page<BookDto> getAll(Pageable page);

    BookDto getById(long id);

    BookDto update(BookDto dto);

    void delete(long id);
}
