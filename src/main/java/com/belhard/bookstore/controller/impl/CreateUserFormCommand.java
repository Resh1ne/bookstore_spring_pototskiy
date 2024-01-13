package com.belhard.bookstore.controller.impl;

import com.belhard.bookstore.controller.Command;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.stereotype.Controller;

@Controller("create_user_form")
public class CreateUserFormCommand implements Command {
    @Override
    public String execute(HttpServletRequest req) {
        return "jsp/user/createUserForm.jsp";
    }
}
