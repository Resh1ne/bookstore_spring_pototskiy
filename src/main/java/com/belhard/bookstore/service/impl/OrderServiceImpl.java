package com.belhard.bookstore.service.impl;

import com.belhard.bookstore.data.entity.Order;
import com.belhard.bookstore.data.entity.OrderInfo;
import com.belhard.bookstore.data.entity.enums.Status;
import com.belhard.bookstore.data.repository.OrderRepository;
import com.belhard.bookstore.service.OrderService;
import com.belhard.bookstore.service.dto.OrderCreateDto;
import com.belhard.bookstore.service.dto.OrderReadFullDto;
import com.belhard.bookstore.service.dto.OrderReadSimpleDto;
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
    public OrderReadFullDto create(OrderCreateDto dto) {
        log.debug("Creating order.");
        Order order = toCreateEntity(dto);
        Order createdOrder = orderRepository.create(order);
        return toOrderReadFullDto(createdOrder);
    }

    private OrderReadFullDto toOrderReadFullDto(Order createdOrder) {
        OrderReadFullDto dto = new OrderReadFullDto();
        dto.setId(createdOrder.getId());
        dto.setUser(createdOrder.getUser());
        dto.setItems(createdOrder.getItems());
        dto.setTotalCost(calculateTotalCost(createdOrder));
        dto.setStatus(Status.valueOf(createdOrder.getStatus().name()));
        updateTotalCost(createdOrder.getId());
        return dto;
    }

    private OrderReadSimpleDto toOrderReadSimpleDto(Order createdOrder) {
        OrderReadSimpleDto dto = new OrderReadSimpleDto();
        dto.setUser(createdOrder.getUser());
        dto.setTotalCost(calculateTotalCost(createdOrder));
        dto.setStatus(Status.valueOf(createdOrder.getStatus().name()));
        updateTotalCost(createdOrder.getId());
        return dto;
    }

    private Order toCreateEntity(OrderCreateDto dto) {
        Order entity = new Order();
        entity.setUser(dto.getUser());
        entity.setItems(dto.getItems());
        entity.setTotalCost(dto.getTotalCost());
        entity.setStatus(Status.valueOf(dto.getStatus().name()));
        return entity;
    }

    @Override
    public List<OrderReadSimpleDto> getAll() {
        log.debug("Getting all orders.");
        return orderRepository
                .findAll()
                .stream()
                .map(this::toOrderReadSimpleDto)
                .toList();
    }

    @Override
    public List<OrderReadFullDto> getAllFull() {
        log.debug("Getting all orders with details.");
        return orderRepository
                .findAll()
                .stream()
                .map(this::toOrderReadFullDto)
                .toList();
    }

    @Override
    public OrderReadFullDto getById(long id) {
        log.debug("Getting order by ID: {}", id);
        Order order = orderRepository.findById(id);
        if (order == null) {
            throw new RuntimeException("No order with ID: " + id);
        }
        return toOrderReadFullDto(order);
    }

    @Override
    public OrderReadFullDto update(OrderReadFullDto dto) {
        log.debug("Updating order.");
        Order order = toUpdateEntity(dto);
        Order updatedOrder = orderRepository.update(order);
        return toOrderReadFullDto(updatedOrder);
    }

    private Order toUpdateEntity(OrderReadFullDto dto) {
        Order entity = new Order();
        entity.setId(dto.getId());
        entity.setUser(dto.getUser());
        entity.setItems(dto.getItems());
        entity.setTotalCost(dto.getTotalCost());
        entity.setStatus(Status.valueOf(dto.getStatus().name()));
        return entity;
    }

    @Override
    public void delete(long id) {
        log.debug("Deleting order with ID: {}", id);
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
