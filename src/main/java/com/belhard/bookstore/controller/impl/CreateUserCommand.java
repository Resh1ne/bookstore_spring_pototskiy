package com.belhard.bookstore.controller.impl;

import com.belhard.bookstore.controller.Command;
import com.belhard.bookstore.controller.FrontController;
import com.belhard.bookstore.service.UserService;
import com.belhard.bookstore.service.dto.UserDto;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;

@Controller("create_user")
@RequiredArgsConstructor
public class CreateUserCommand implements Command {
    private final UserService userService;

    @Override
    public String execute(HttpServletRequest req) {
        String email = req.getParameter("email");
        String password = req.getParameter("password");
        UserDto user = new UserDto();
        user.setEmail(email);
        user.setPassword(password);

        UserDto createdUser = userService.create(user);
        return FrontController.REDIRECT + "controller?command=user&id=" + createdUser.getId();
    }
}
