package com.belhard.bookstore.controller.impl;

import com.belhard.bookstore.controller.Command;
import com.belhard.bookstore.controller.FrontController;
import com.belhard.bookstore.data.entity.enums.GenresOfTheBook;
import com.belhard.bookstore.data.entity.enums.LanguagesOfTheBook;
import com.belhard.bookstore.service.BookService;
import com.belhard.bookstore.service.dto.BookDto;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;

import java.math.BigDecimal;

@RequiredArgsConstructor
@Controller("edit_book")
public class EditBookCommand implements Command {
    private final BookService bookService;

    @Override
    public String execute(HttpServletRequest req) {
        BookDto book = process(req);

        BookDto createdBook = bookService.update(book);

        return FrontController.REDIRECT + "controller?command=book&id=" + createdBook.getId();
    }

    private static BookDto process(HttpServletRequest req) {
        long id = Long.parseLong(req.getParameter("id"));
        String title = req.getParameter("title");
        String isbn = req.getParameter("isbn");
        String author = req.getParameter("author");
        Integer pages = Integer.parseInt(req.getParameter("pages"));
        Integer publicationYear = Integer.parseInt(req.getParameter("publicationYear"));
        String genre = req.getParameter("genre");
        String language = req.getParameter("language");
        double rawPrice = Double.parseDouble(req.getParameter("price"));
        BigDecimal price = BigDecimal.valueOf(rawPrice);
        BookDto book = new BookDto();
        book.setId(id);
        book.setTitle(title);
        book.setIsbn(isbn);
        book.setAuthor(author);
        book.setPages(pages);
        book.setPublicationYear(publicationYear);
        book.setGenre(GenresOfTheBook.valueOf(genre));
        book.setLanguage(LanguagesOfTheBook.valueOf(language));
        book.setPrice(price);
        return book;
    }
}
