package com.belhard.bookstore.controller.impl;

import com.belhard.bookstore.controller.Command;
import com.belhard.bookstore.controller.FrontController;
import com.belhard.bookstore.data.entity.User;
import com.belhard.bookstore.service.OrderService;
import com.belhard.bookstore.service.dto.OrderCreateDto;
import com.belhard.bookstore.service.dto.OrderReadFullDto;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;

@Controller("create_user")
@RequiredArgsConstructor
public class CreateOrderCommand implements Command {
    private final OrderService orderService;

    @Override
    public String execute(HttpServletRequest req) {
        OrderCreateDto orderCreateDto = processReq(req);

        OrderReadFullDto created = orderService.create(orderCreateDto);

        return FrontController.REDIRECT + "controller?command=order&id=" + created.getId();
    }

    private OrderCreateDto processReq(HttpServletRequest req) {
        String email = req.getParameter("email");
        String password = req.getParameter("password");
        OrderCreateDto dto = new OrderCreateDto();
        dto.setUser(new User());
        return dto;
    }
}
