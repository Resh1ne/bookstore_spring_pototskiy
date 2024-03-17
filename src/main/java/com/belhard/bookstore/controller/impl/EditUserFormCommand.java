package com.belhard.bookstore.controller.impl;

import com.belhard.bookstore.controller.Command;
import com.belhard.bookstore.service.UserService;
import com.belhard.bookstore.service.dto.UserDto;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;

@Controller("edit_user_form")
@RequiredArgsConstructor
public class EditUserFormCommand implements Command {
    private final UserService userService;

    @Override
    public String execute(HttpServletRequest req) {
        String rawId = req.getParameter("id");
        long id = Long.parseLong(rawId);
        UserDto user = userService.getById(id);
        req.setAttribute("user", user);
        return "jsp/user/editUserForm.jsp";
    }
}
