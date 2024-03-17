package com.belhard.bookstore.data.entity;

import com.belhard.bookstore.data.entity.enums.GenresOfTheBook;
import lombok.Data;

import java.math.BigDecimal;

@Data
public class Book {
    private Long id;
    private String isbn;
    private String author;
    private String title;
    private Integer pages;
    private Integer publicationYear;
    private GenresOfTheBook genre;
    private Language language;
    private BigDecimal price;

    public enum Language {
        ENGLISH, CHINESE, SPANISH, ARABIC, FRENCH, RUSSIAN, PORTUGUESE, JAPANESE, GERMAN, KOREAN, TURKISH, ITALIAN
    }
}
