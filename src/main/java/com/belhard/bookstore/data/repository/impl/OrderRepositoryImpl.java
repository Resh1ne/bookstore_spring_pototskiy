package com.belhard.bookstore.data.repository.impl;

import com.belhard.bookstore.data.entity.Order;
import com.belhard.bookstore.data.repository.OrderRepository;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Repository
@Transactional
public class OrderRepositoryImpl implements OrderRepository {
    private static final String GET_ALL = "from Order";
    private static final String FIND_BY_USER_ID = "SELECT o FROM Order o WHERE o.user.id = :userId";
    @PersistenceContext
    private EntityManager manager;

    @Override
    public List<Order> findByUserId(Long userId) {
        return manager
                .createQuery(FIND_BY_USER_ID, Order.class)
                .setParameter("userId", userId)
                .getResultList();
    }

    @Override
    public Optional<Order> findById(Long key) {
        return Optional.ofNullable(manager.find(Order.class, key));
    }

    @Override
    public List<Order> findAll() {
        return manager.createQuery(GET_ALL, Order.class).getResultList();
    }

    @Override
    public Order save(Order entity) {
        if (entity.getId() == null) {
            manager.persist(entity);
            return entity;
        } else {
            return manager.merge(entity);
        }
    }

    @Override
    public boolean delete(Long key) {
        Order order = manager.find(Order.class, key);
        if (order == null) {
            return false;
        }
        manager.remove(order);
        return true;
    }
}

