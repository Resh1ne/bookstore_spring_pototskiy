package com.belhard.bookstore.controller.impl;

import com.belhard.bookstore.controller.Command;
import com.belhard.bookstore.service.UserService;
import com.belhard.bookstore.service.dto.UserDto;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;

import java.time.LocalDateTime;

@RequiredArgsConstructor
public class UserCommand implements Command {
    private final UserService userService;

    @Override
    public String execute(HttpServletRequest req) {
        String idRaw = req.getParameter("id");
        long id = Long.parseLong(idRaw);
        UserDto user = userService.getById(id);
        req.setAttribute("user", user);
        req.setAttribute("date", LocalDateTime.now().toString());
        return "jsp/user/user.jsp";
    }
}
