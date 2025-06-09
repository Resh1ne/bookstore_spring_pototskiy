package com.belhard.bookstore.data.dao;

import com.belhard.bookstore.data.dto.OrderDto;

import java.util.List;

public interface OrderDao {
    OrderDto create(OrderDto order);

    OrderDto findById(Long id);

    List<OrderDto> findAll();

    OrderDto update(OrderDto order);

    boolean delete(Long id);
}
