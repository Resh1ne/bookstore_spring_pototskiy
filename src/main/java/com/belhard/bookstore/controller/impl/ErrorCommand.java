package com.belhard.bookstore.controller.impl;

import com.belhard.bookstore.controller.Command;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.stereotype.Controller;

@Controller("error")
public class ErrorCommand implements Command {
    @Override
    public String execute(HttpServletRequest req) {
        return "jsp/error/error.jsp";
    }
}
