package com.belhard.bookstore.controller.impl;

import com.belhard.bookstore.controller.Command;
import com.belhard.bookstore.service.OrderService;
import com.belhard.bookstore.service.dto.OrderReadFullDto;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;

import java.util.List;

@RequiredArgsConstructor
@Controller("orders")
public class OrdersCommand implements Command {
    private final OrderService orderService;

    @Override
    public String execute(HttpServletRequest req) {
        List<OrderReadFullDto> orders = orderService.getAllFull();
        req.setAttribute("orders", orders);
        return "/jsp/orders.jsp";
    }
}

