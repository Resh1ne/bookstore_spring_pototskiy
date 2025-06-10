package com.belhard.bookstore.controller.impl;

import com.belhard.bookstore.controller.Command;
import com.belhard.bookstore.controller.FrontController;
import com.belhard.bookstore.data.entity.Book;
import com.belhard.bookstore.data.entity.enums.GenresOfTheBook;
import com.belhard.bookstore.data.entity.enums.Language;
import com.belhard.bookstore.service.BookService;
import com.belhard.bookstore.service.dto.BookDto;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;

@RequiredArgsConstructor
@Controller("create_book")
public class CreateBookCommand implements Command {
    private final BookService bookService;

    @Override
    public String execute(HttpServletRequest req) {
        BookDto book = process(req);

        BookDto createdBook = bookService.create(book);

        return FrontController.REDIRECT + "controller?command=book&id=" + createdBook.getId();
    }

    private static BookDto process(HttpServletRequest req) {
        String title = req.getParameter("title");
        String isbn = req.getParameter("isbn");
        String language = req.getParameter("language");
        String genre = req.getParameter("genre");
        BookDto book = new BookDto();
        book.setTitle(title);
        book.setIsbn(isbn);
        book.setLanguage(Language.valueOf(language));
        book.setGenre(GenresOfTheBook.valueOf(genre));
        return book;
    }
}
