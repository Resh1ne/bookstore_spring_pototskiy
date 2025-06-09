package com.belhard.bookstore.service.impl;

import com.belhard.bookstore.data.entity.Order;
import com.belhard.bookstore.data.entity.OrderInfo;
import com.belhard.bookstore.data.entity.enums.Status;
import com.belhard.bookstore.data.repository.OrderRepository;
import com.belhard.bookstore.service.OrderService;
import com.belhard.bookstore.service.dto.OrderDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;

@Service
@Log4j2
@RequiredArgsConstructor
public class OrderServiceImpl implements OrderService {
    private final OrderRepository orderRepository;

    @Override
    public OrderDto create(OrderDto dto) {
        log.debug("Creating order");
        Order order = toOrderEntity(dto);
        Order orderCreated = orderRepository.create(order);
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

    private Order toOrderEntity(OrderDto dto) {
        Order entity = new Order();
        entity.setUser(dto.getUser());
        entity.setItems(dto.getItems());
        entity.setTotalCost(dto.getTotalCost());
        entity.setStatus(Status.valueOf(dto.getStatus().name()));
        return entity;
    }

    @Override
    public List<OrderDto> getAll() {
        log.debug("Getting all orders with details.");
        return orderRepository
                .findAll()
                .stream()
                .map(this::toOrderDto)
                .toList();
    }

    @Override
    public OrderDto getById(long id) {
        log.debug("Getting order by ID: {}", id);
        Order orderEntity = orderRepository.findById(id);
        if (orderEntity == null) {
            throw new RuntimeException("No order with ID: " + id);
        }
        return toOrderDto(orderEntity);
    }

    @Override
    public OrderDto update(OrderDto dto) {
        log.debug("Updating order.");
        Order order = toOrderEntity(dto);
        order.setId(dto.getId());
        Order orderCreated = orderRepository.update(order);
        return toOrderDto(orderCreated);
    }

    @Override
    public void delete(long id) {
        log.debug("Deleting order with ID: {}", id);
        Order order = orderRepository.findById(id);
        if (order == null) {
            throw new RuntimeException("Order with id: " + id + " not found");
        }
        orderRepository.delete(id);
    }

    public BigDecimal calculateTotalCost(Order order) {
        BigDecimal totalCost = BigDecimal.ZERO;

        for (OrderInfo orderInfo : order.getItems()) {
            BigDecimal itemCost = orderInfo.getBookPrice().multiply(BigDecimal.valueOf(orderInfo.getBookQuantity()));
            totalCost = totalCost.add(itemCost);
        }
        return totalCost;
    }

    public void updateTotalCost(Long orderId) {
        Order order = orderRepository.findById(orderId);
        if (order != null) {
            BigDecimal totalCost = calculateTotalCost(order);
            order.setTotalCost(totalCost);
            orderRepository.update(order);
        }
    }
}
