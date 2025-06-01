package com.belhard.bookstore.controller.impl;

import com.belhard.bookstore.controller.Command;
import com.belhard.bookstore.service.OrderService;
import com.belhard.bookstore.service.dto.OrderDto;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;

@RequiredArgsConstructor
@Controller("order")
public class OrderCommand implements Command {
    private final OrderService orderService;

    @Override
    public String execute(HttpServletRequest req) {
        String idRaw = req.getParameter("id");
        long id = Long.parseLong(idRaw);
        OrderDto order = orderService.getById(id);
        req.setAttribute("order", order);
        return "/jsp/order/order.jsp";
    }
}
