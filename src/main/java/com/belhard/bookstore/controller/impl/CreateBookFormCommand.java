package com.belhard.bookstore.controller.impl;

import com.belhard.bookstore.controller.Command;
import jakarta.servlet.http.HttpServletRequest;

public class CreateBookFormCommand implements Command {
    @Override
    public String execute(HttpServletRequest req) {
        return "jsp/book/createBookForm.jsp";
    }
}
