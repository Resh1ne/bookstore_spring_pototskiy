package com.belhard.bookstore.service;

import com.belhard.bookstore.service.dto.OrderCreateDto;
import com.belhard.bookstore.service.dto.OrderReadFullDto;
import com.belhard.bookstore.service.dto.OrderReadSimpleDto;

import java.util.List;

public interface OrderService {
    OrderReadFullDto create(OrderCreateDto dto);

    List<OrderReadSimpleDto> getAll();

    List<OrderReadFullDto> getAllFull();

    OrderReadFullDto getById(long id);

    OrderReadFullDto update(OrderReadFullDto dto);

    void delete(long id);
}
