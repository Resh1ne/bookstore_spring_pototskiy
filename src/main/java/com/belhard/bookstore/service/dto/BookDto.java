package com.belhard.bookstore.service.dto;

import com.belhard.bookstore.data.entity.Book;
import com.belhard.bookstore.data.entity.enums.GenresOfTheBook;
import lombok.Data;

import java.math.BigDecimal;

@Data
public class BookDto {
    private Long id;
    private String isbn;
    private String author;
    private String title;
    private Integer pages;
    private Integer publicationYear;
    private GenresOfTheBook genre;
    private Book.Language language;
    private BigDecimal price;
}
