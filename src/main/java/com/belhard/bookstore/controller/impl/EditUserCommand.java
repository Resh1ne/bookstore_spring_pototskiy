package com.belhard.bookstore.controller.impl;

import com.belhard.bookstore.controller.Command;
import com.belhard.bookstore.data.entity.enums.Role;
import com.belhard.bookstore.service.UserService;
import com.belhard.bookstore.service.dto.UserDto;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class EditUserCommand implements Command {
    private final UserService userService;
    @Override
    public String execute(HttpServletRequest req) {
        UserDto user = process(req);

        UserDto createdUser = userService.update(user);

        req.setAttribute("user", createdUser);
        return "jsp/user/user.jsp";
    }

    private static UserDto process(HttpServletRequest req) {
        long id = Long.parseLong(req.getParameter("id"));
        String email = req.getParameter("email");
        String password = req.getParameter("password");
        String firstName = req.getParameter("firstName");
        String lastName = req.getParameter("lastName");
        Integer age = Integer.parseInt(req.getParameter("age"));
        String role = req.getParameter("role");
        UserDto user = new UserDto();
        user.setId(id);
        user.setEmail(email);
        user.setPassword(password);
        user.setFirstName(firstName);
        user.setLastName(lastName);
        user.setAge(age);
        user.setRole(Role.valueOf(role));
        return user;
    }
}
