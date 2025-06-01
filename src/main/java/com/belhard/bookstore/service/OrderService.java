package com.belhard.bookstore.service;

import com.belhard.bookstore.service.dto.OrderDto;

import java.util.List;

public interface OrderService {
    OrderDto create(OrderDto dto);

    List<OrderDto> getAll();

    OrderDto getById(long id);

    OrderDto update(OrderDto dto);

    void delete(long id);
}
