package com.belhard.bookstore.data.repository;

import com.belhard.bookstore.data.entity.Order;

import java.util.List;

public interface OrderRepository {
    Order create(Order order);

    Order findById(Long id);

    List<Order> findAll();

    Order update(Order order);

    boolean delete(Long id);
}
