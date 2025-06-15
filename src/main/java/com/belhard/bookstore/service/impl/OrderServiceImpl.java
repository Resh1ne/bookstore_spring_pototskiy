package com.belhard.bookstore.service.impl;

import com.belhard.bookstore.data.entity.Order;
import com.belhard.bookstore.data.entity.OrderInfo;
import com.belhard.bookstore.data.repository.OrderRepository;
import com.belhard.bookstore.service.OrderService;
import com.belhard.bookstore.service.dto.OrderDto;
import com.belhard.bookstore.service.dto.OrderSimpleDto;
import com.belhard.bookstore.service.exception.ResourceNotFoundException;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.hibernate.Hibernate;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

@Service
@Log4j2
@RequiredArgsConstructor
public class OrderServiceImpl implements OrderService {
    private final OrderRepository orderRepository;
    private final ModelMapper mapper;

    @Override
    public OrderDto create(OrderDto dto) {
        Order order = mapper.map(dto, Order.class);
        Order orderCreated = orderRepository.save(order);
        log.info("Created new order with id: {}", orderCreated.getId());
        return mapper.map(orderCreated, OrderDto.class);
    }

    @Override
    public List<OrderSimpleDto> getAll() {
        log.debug("Getting all orders with details.");
        return orderRepository
                .findAll()
                .stream()
                .map(e -> mapper.map(e, OrderSimpleDto.class))
                .toList();
    }

    @Override
    @Transactional
    public OrderDto getById(long id) {
        Order order = orderRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Order not found with id: " + id));
        Hibernate.initialize(order.getItems());
        Hibernate.initialize(order.getUser());
        return mapper.map(order, OrderDto.class);
    }

    @Override
    public OrderDto update(OrderDto dto) {
        Order order = mapper.map(dto, Order.class);
        Order orderCreated = orderRepository.save(order);
        log.info("Updated order with id: {}", orderCreated.getId());
        return mapper.map(orderCreated, OrderDto.class);
    }

    @Override
    public void delete(long id) {
        if (!orderRepository.existsById(id)) {
            throw new ResourceNotFoundException("Order with id: " + id + " not found");
        }
        orderRepository.deleteById(id);
        log.info("Deleted order with id: {}", id);
    }

    public BigDecimal calculateTotalCost(Order order) {
        BigDecimal totalCost = BigDecimal.ZERO;

        for (OrderInfo orderInfo : order.getItems()) {
            BigDecimal itemCost = orderInfo.getPrice().multiply(BigDecimal.valueOf(orderInfo.getQuantity()));
            totalCost = totalCost.add(itemCost);
        }
        return totalCost;
    }

    public void updateTotalCost(Long orderId) {
        Optional<Order> order = orderRepository.findById(orderId);
        if (order.isPresent()) {
            BigDecimal totalCost = calculateTotalCost(order.get());
            order.get().setTotalCost(totalCost);
            orderRepository.save(order.get());
        }
    }
}
