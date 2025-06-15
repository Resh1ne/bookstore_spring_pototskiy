package com.belhard.bookstore.data.repository;

import com.belhard.bookstore.data.entity.Order;
import com.belhard.bookstore.data.entity.User;
import com.belhard.bookstore.data.entity.enums.Status;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface OrderRepository extends JpaRepository<Order, Long> {
    List<Order> findByUser(User user);
    Optional<Order> findByUserAndStatus(User user, Status status);
}
