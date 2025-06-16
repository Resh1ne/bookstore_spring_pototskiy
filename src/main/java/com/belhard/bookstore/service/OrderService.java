package com.belhard.bookstore.service;

import com.belhard.bookstore.service.dto.OrderDto;
import com.belhard.bookstore.service.dto.OrderSimpleDto;
import com.belhard.bookstore.service.dto.UserDto;

import java.util.List;

public interface OrderService {
    OrderDto create(OrderDto dto);

    List<OrderSimpleDto> getAll();

    OrderDto getById(long id);

    OrderDto update(OrderDto dto);

    void delete(long id);

    OrderDto getCurrentPendingOrder(UserDto user);

    void addBookToOrder(Long bookId, UserDto user);

    void payOrder(long id);

    void removeBookFromOrder(Long bookId, UserDto user);
}
