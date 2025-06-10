package com.belhard.bookstore.data.repository;

import com.belhard.bookstore.data.entity.Order;

import java.util.List;

public interface OrderRepository extends CrudRepository<Order, Long> {
    List<Order> findByUserId(Long userId);
}
