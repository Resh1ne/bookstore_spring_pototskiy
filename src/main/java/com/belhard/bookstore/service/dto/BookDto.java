package com.belhard.bookstore.service.dto;

import com.belhard.bookstore.data.entity.enums.GenresOfTheBook;
import com.belhard.bookstore.data.entity.enums.LanguagesOfTheBook;
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
    private LanguagesOfTheBook language;
    private BigDecimal price;
}
