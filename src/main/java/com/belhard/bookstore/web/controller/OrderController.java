package com.belhard.bookstore.web.controller;

import com.belhard.bookstore.service.OrderService;
import com.belhard.bookstore.service.dto.OrderDto;
import com.belhard.bookstore.service.dto.OrderSimpleDto;
import com.belhard.bookstore.service.dto.UserDto;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Controller
@RequestMapping("/orders")
@RequiredArgsConstructor
public class OrderController {
    private final OrderService orderService;

    @GetMapping("/{id}")
    public String getOrder(@PathVariable Long id, Model model) {
        OrderDto order = orderService.getById(id);
        model.addAttribute("order", order);
        return "order/order";
    }

    @GetMapping
    public String getOrders(Model model) {
        List<OrderSimpleDto> orders = orderService.getAll();
        model.addAttribute("orders", orders);
        return "order/orders";
    }

    @PostMapping("/add/{bookId}")
    public String addToCart(@PathVariable Long bookId, HttpSession session) {
        UserDto currentUser = (UserDto) session.getAttribute("user");
        orderService.addBookToOrder(bookId, currentUser);
        return "redirect:/books";
    }

    @GetMapping("/my")
    public String getCurrentUserOrder(HttpSession session, Model model) {
        UserDto currentUser = (UserDto) session.getAttribute("user");
        OrderDto order = orderService.getCurrentPendingOrder(currentUser);
        model.addAttribute("order", order);
        return "order/myorder";
    }
}
