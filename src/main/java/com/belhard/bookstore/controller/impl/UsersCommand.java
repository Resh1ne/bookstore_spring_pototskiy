package com.belhard.bookstore.controller.impl;

import com.belhard.bookstore.controller.Command;
import com.belhard.bookstore.service.UserService;
import com.belhard.bookstore.service.dto.UserDto;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;

import java.util.List;

@RequiredArgsConstructor
public class UsersCommand implements Command {
    private final UserService userService;

    @Override
    public String execute(HttpServletRequest req) {
        List<UserDto> users = userService.getAll();
        req.setAttribute("users", users);
        return "jsp/user/users.jsp";
    }
}
