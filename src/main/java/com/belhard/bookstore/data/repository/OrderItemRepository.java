package com.belhard.bookstore.data.repository;

import com.belhard.bookstore.data.entity.Book;
import com.belhard.bookstore.data.entity.Order;
import com.belhard.bookstore.data.entity.OrderItem;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface OrderItemRepository extends JpaRepository<OrderItem, Long> {
    Optional<OrderItem> findByOrderAndBook(Order order, Book book);
}
