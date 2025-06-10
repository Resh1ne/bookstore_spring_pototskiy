package com.belhard.bookstore.service.impl;

import com.belhard.bookstore.data.entity.Order;
import com.belhard.bookstore.data.entity.OrderInfo;
import com.belhard.bookstore.data.entity.enums.Status;
import com.belhard.bookstore.data.repository.OrderRepository;
import com.belhard.bookstore.service.OrderService;
import com.belhard.bookstore.service.dto.OrderDto;
import com.belhard.bookstore.service.dto.OrderSimpleDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.hibernate.Hibernate;
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

    @Override
    public OrderDto create(OrderDto dto) {
        Order order = toOrderEntity(dto);
        Order orderCreated = orderRepository.save(order);
        log.info("Created new order with id: {}", orderCreated.getId());
        return toOrderDto(orderCreated);
    }

    private OrderDto toOrderDto(Order order) {
        OrderDto orderDto = new OrderDto();
        orderDto.setId(order.getId());
        orderDto.setUser(order.getUser());
        orderDto.setItems(order.getItems());
        orderDto.setTotalCost(order.getTotalCost());
        orderDto.setStatus(order.getStatus());
        return orderDto;
    }

    private OrderSimpleDto toOrderSimpleDto(Order order) {
        OrderSimpleDto orderDto = new OrderSimpleDto();
        orderDto.setId(order.getId());
        orderDto.setTotalCost(order.getTotalCost());
        orderDto.setStatus(order.getStatus());
        return orderDto;
    }

    private Order toOrderEntity(OrderDto dto) {
        Order entity = new Order();
        entity.setUser(dto.getUser());
        entity.setItems(dto.getItems());
        entity.setTotalCost(dto.getTotalCost());
        entity.setStatus(Status.valueOf(dto.getStatus().name()));
        return entity;
    }

    @Override
    public List<OrderSimpleDto> getAll() {
        log.debug("Getting all orders with details.");
        return orderRepository
                .findAll()
                .stream()
                .map(this::toOrderSimpleDto)
                .toList();
    }

    @Override
    @Transactional
    public OrderDto getById(long id) {
        Order order = orderRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Order not found with id: " + id));
        Hibernate.initialize(order.getItems());
        Hibernate.initialize(order.getUser());
        return toOrderDto(order);
    }

    @Override
    public OrderDto update(OrderDto dto) {
        Order order = toOrderEntity(dto);
        order.setId(dto.getId());
        Order orderCreated = orderRepository.save(order);
        log.info("Updated order with id: {}", orderCreated.getId());
        return toOrderDto(orderCreated);
    }

    @Override
    public void delete(long id) {
        if (!orderRepository.delete(id)) {
            throw new RuntimeException("Order with id: " + id + " not found");
        }
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
