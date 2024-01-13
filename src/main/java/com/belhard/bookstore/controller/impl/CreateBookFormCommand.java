package com.belhard.bookstore.controller.impl;

import com.belhard.bookstore.controller.Command;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.stereotype.Controller;

@Controller("create_book_form")
public class CreateBookFormCommand implements Command {
    @Override
    public String execute(HttpServletRequest req) {
        return "jsp/book/createBookForm.jsp";
    }
}
