package com.belhard.bookstore.controller.impl;

import com.belhard.bookstore.controller.Command;
import com.belhard.bookstore.service.BookService;
import com.belhard.bookstore.service.dto.BookDto;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;

import java.util.List;

@RequiredArgsConstructor
public class BooksCommand implements Command {
    private final BookService bookService;

    @Override
    public String execute(HttpServletRequest req) {
        List<BookDto> books = bookService.getAll();
        req.setAttribute("books", books);
        return "jsp/book/books.jsp";
    }
}
